package com.lukamaletin.codeshare.controller;

import com.lukamaletin.codeshare.controller.exception.AuthorizationException;
import com.lukamaletin.codeshare.controller.exception.BadRequestException;
import com.lukamaletin.codeshare.controller.exception.NotFoundException;
import com.lukamaletin.codeshare.model.User;
import com.lukamaletin.codeshare.model.dto.PhotoDto;
import com.lukamaletin.codeshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final ServletContext servletContext;

    @Autowired
    public UserController(UserRepository userRepository, ServletContext servletContext) {
        this.userRepository = userRepository;
        this.servletContext = servletContext;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        final User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    public ResponseEntity search(@RequestParam(required = false, defaultValue = "") String username) {
        final List<User> allUsers = userRepository.findAll();
        List<User> filteredUsers = new ArrayList<>();
        final String usernameLowerCase = username.toLowerCase();

        for (User user : allUsers) {
            if (user.getUsername().toLowerCase().contains(usernameLowerCase)) {
                filteredUsers.add(user);
            }
        }
        return new ResponseEntity<>(filteredUsers, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/snippets")
    public ResponseEntity findSnippets(@PathVariable long id) {
        final User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));
        return new ResponseEntity<>(user.getSnippets(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(path = "/{id}/ban")
    public ResponseEntity setBanned(@PathVariable long id, @RequestBody boolean banned) {
        final User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));
        if (user.isAdmin()) {
            throw new BadRequestException("Cannot ban other admins!");
        }

        user.setBanned(banned);
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody User user) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User currentUser = userRepository.findById((Long) auth.getPrincipal())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        if (currentUser.getId() != user.getId() && !currentUser.isAdmin()) {
            throw new AuthorizationException("Only admins can update other users' information!");
        }

        user.setId(id);
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity remove(@PathVariable long id) {
        userRepository.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(path = "uploadPhoto")
    public ResponseEntity uploadPhoto(@RequestParam MultipartFile file) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User currentUser = userRepository.findById((Long) auth.getPrincipal())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        try {
            final String baseUrl = servletContext.getRealPath("/images");
            final String newUrl = String.format("%s/%d.jpg", baseUrl, currentUser.getId());
            final String photoUrl = String.format("/images/%d.jpg", currentUser.getId());

            currentUser.setPhotoUrl(photoUrl);
            userRepository.save(currentUser);

            final File newPhoto = new File(newUrl);
            if (!newPhoto.exists()) {
                newPhoto.createNewFile();
            }
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newPhoto));
            stream.write(bytes);
            stream.close();
            return new ResponseEntity<>(new PhotoDto(photoUrl), HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException();
        }
    }
}
