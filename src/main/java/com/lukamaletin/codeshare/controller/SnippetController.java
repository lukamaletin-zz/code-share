package com.lukamaletin.codeshare.controller;

import com.lukamaletin.codeshare.controller.exception.AuthorizationException;
import com.lukamaletin.codeshare.controller.exception.ForbiddenException;
import com.lukamaletin.codeshare.controller.exception.NotFoundException;
import com.lukamaletin.codeshare.model.Comment;
import com.lukamaletin.codeshare.model.Snippet;
import com.lukamaletin.codeshare.model.User;
import com.lukamaletin.codeshare.repository.CommentRepository;
import com.lukamaletin.codeshare.repository.SnippetRepository;
import com.lukamaletin.codeshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/snippets")
public class SnippetController {

    private final SnippetRepository snippetRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public SnippetController(SnippetRepository snippetRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.snippetRepository = snippetRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        final Snippet snippet = snippetRepository.findById(id).orElseThrow(() -> new NotFoundException("Snippet not found!"));

        if (snippet.isExpired()) {
            snippetRepository.delete(snippet.getId());
            throw new NotFoundException("Snippet not found!");
        }

        return new ResponseEntity<>(snippet, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(snippetRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    public ResponseEntity search(@RequestParam(required = false, defaultValue = "") String description,
                                 @RequestParam(required = false, defaultValue = "") String language,
                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        if (startDate == null) {
            startDate = new Date(Long.MIN_VALUE);
        }
        if (endDate == null) {
            endDate = new Date();
        }
        final String descriptionLowerCase = description.toLowerCase();
        final List<Snippet> allSnippets = snippetRepository.findAll();
        List<Snippet> filteredSnippets = new ArrayList<>();

        for (Snippet snippet : allSnippets) {
            if (snippet.getDescription().toLowerCase().contains(descriptionLowerCase) &&
                    snippet.getDate().after(startDate) &&
                    snippet.getDate().before(endDate)) {
                if (language.equals("") || snippet.getLanguage().getLanguageName().equalsIgnoreCase(language)) {
                    filteredSnippets.add(snippet);
                }
            }
        }

        return new ResponseEntity<>(filteredSnippets, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Snippet snippet) {
        User currentUser;
        try {
            final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            currentUser = userRepository.findById((Long) auth.getPrincipal())
                    .orElseThrow(() -> new NotFoundException("User not found!"));
        } catch (Exception e) {
            currentUser = null;
        }
        if (currentUser != null && currentUser.isBanned()) {
            throw new ForbiddenException("User is banned!");
        }

        snippet.setUser(currentUser);
        snippet.setDate(new Date());
        snippetRepository.save(snippet);
        return new ResponseEntity<>(snippet.getId(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(path = "/{id}/ban")
    public ResponseEntity setBanned(@PathVariable long id, @RequestBody boolean banned) {
        final Snippet snippet = snippetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Snippet not found!"));
        snippet.setBanned(banned);
        snippetRepository.save(snippet);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody Snippet snippet) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User currentUser = userRepository.findById((Long) auth.getPrincipal())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        if (snippet.getUser() != null && currentUser.getId() != snippet.getUser().getId() && !currentUser.isAdmin()) {
            throw new AuthorizationException("Only admins can update other users' snippets!");
        }
        if (!snippetRepository.findById(id).isPresent()) {
            throw new NotFoundException("Snippet not found!");
        }

        snippet.setId(id);
        snippetRepository.save(snippet);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity remove(@PathVariable long id) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User currentUser = userRepository.findById((Long) auth.getPrincipal())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        final Snippet snippet = snippetRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Snippet not found!"));

        if (snippet.getUser() != null && currentUser.getId() != snippet.getUser().getId()
                && !currentUser.isAdmin()) {
            throw new AuthorizationException("Only admins can remove other users' snippets!");
        }

        snippetRepository.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(path = "/{snippetId}/comments")
    public ResponseEntity createComment(@PathVariable long snippetId, @RequestBody Comment comment) {
        User currentUser;
        try {
            final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            currentUser = userRepository.findById((Long) auth.getPrincipal())
                    .orElseThrow(() -> new NotFoundException("User not found!"));
        } catch (Exception e) {
            currentUser = null;
        }
        if (currentUser != null && currentUser.isBanned()) {
            throw new ForbiddenException("User is banned!");
        }
        final Snippet snippet = snippetRepository.findById(snippetId)
                .orElseThrow(() -> new NotFoundException("Snippet not found!"));
        if (snippet.isBanned()) {
            throw new ForbiddenException("Snippet is banned!");
        }

        comment.setUser(currentUser);
        comment.setSnippet(snippet);
        comment.setDate(new Date());
        commentRepository.save(comment);
        return new ResponseEntity<>(comment.getId(), HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(path = "/{snippetId}/comments/{commentId}")
    public ResponseEntity removeComment(@PathVariable long snippetId, @PathVariable long commentId) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User currentUser = userRepository.findById((Long) auth.getPrincipal())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        final Snippet snippet = snippetRepository.findById(snippetId)
                .orElseThrow(() -> new NotFoundException("Snippet not found!"));

        Comment comment = null;
        for (Comment c : snippet.getComments()) {
            if (c.getId() == commentId) {
                comment = c;
                break;
            }
        }
        if (comment == null) {
            throw new NotFoundException("Comment not found!");
        }
        if (comment.getUser() != null && currentUser.getId() != comment.getUser().getId() && !currentUser.isAdmin()) {
            throw new AuthorizationException("Only admins can remove other users' comments!");
        }

        commentRepository.delete(commentId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
