package com.lukamaletin.codeshare.repository;

import com.lukamaletin.codeshare.model.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SnippetRepository extends JpaRepository<Snippet, Long> {

    Optional<Snippet> findById(long id);

    List<Snippet> findAll();
}
