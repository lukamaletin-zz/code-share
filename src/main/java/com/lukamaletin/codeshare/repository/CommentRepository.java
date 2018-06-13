package com.lukamaletin.codeshare.repository;

import com.lukamaletin.codeshare.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findById(long id);

    List<Comment> findAll();
}
