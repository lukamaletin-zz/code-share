package com.lukamaletin.codeshare.controller;

import com.lukamaletin.codeshare.controller.exception.AuthorizationException;
import com.lukamaletin.codeshare.controller.exception.ForbiddenException;
import com.lukamaletin.codeshare.controller.exception.NotFoundException;
import com.lukamaletin.codeshare.model.Comment;
import com.lukamaletin.codeshare.model.Score;
import com.lukamaletin.codeshare.model.User;
import com.lukamaletin.codeshare.repository.CommentRepository;
import com.lukamaletin.codeshare.repository.ScoreRepository;
import com.lukamaletin.codeshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository, ScoreRepository scoreRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.scoreRepository = scoreRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        final Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found!"));
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/{commentId}/scores")
    public ResponseEntity createScore(@PathVariable long commentId, @RequestBody Score score) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User currentUser = userRepository.findById((Long) auth.getPrincipal())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        if (currentUser.isBanned()) {
            throw new ForbiddenException("User is banned!");
        }
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found!"));

        score.setUser(currentUser);
        score.setComment(comment);
        comment.addScore(score);
        scoreRepository.save(score);
        commentRepository.save(comment);
        return new ResponseEntity<>(score.getId(), HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(path = "/{commentId}/scores/{scoreId}")
    public ResponseEntity removeScore(@PathVariable long commentId, @PathVariable long scoreId) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final User currentUser = userRepository.findById((Long) auth.getPrincipal())
                .orElseThrow(() -> new NotFoundException("User not found!"));
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found!"));
        final Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new NotFoundException("Score not found!"));

        if (currentUser.getId() != score.getUser().getId() && !currentUser.isAdmin()) {
            throw new AuthorizationException("Only admins can remove other users' scores!");
        }

        scoreRepository.delete(scoreId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
