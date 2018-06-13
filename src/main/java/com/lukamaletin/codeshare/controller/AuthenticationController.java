package com.lukamaletin.codeshare.controller;

import com.lukamaletin.codeshare.controller.exception.BadRequestException;
import com.lukamaletin.codeshare.controller.exception.NotFoundException;
import com.lukamaletin.codeshare.model.User;
import com.lukamaletin.codeshare.model.dto.LoginDto;
import com.lukamaletin.codeshare.model.dto.RegisterDto;
import com.lukamaletin.codeshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthenticationController {

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/signup")
    public ResponseEntity signup(@RequestBody RegisterDto registerDto) {
        if (registerDto.getUsername() == null || registerDto.getPassword() == null) {
            throw new BadRequestException("Please enter username/password!");
        }
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new BadRequestException("Username taken!");
        }

        final User user = new User(registerDto);
        userRepository.save(user);
        return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
    }

    @PostMapping(path = "/signin")
    public ResponseEntity signin(@RequestBody LoginDto loginDto) {
        final User user = userRepository.findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword())
                .orElseThrow(() -> new NotFoundException("Wrong username/password!"));

        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        final Authentication authentication = new PreAuthenticatedAuthenticationToken(user.getId(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/signout")
    public ResponseEntity signout() {
        SecurityContextHolder.clearContext();
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/authenticate")
    public ResponseEntity authenticate() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            User currentUser = userRepository.findById((Long) auth.getPrincipal())
                    .orElseThrow(() -> new NotFoundException("User not found!"));
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }
}
