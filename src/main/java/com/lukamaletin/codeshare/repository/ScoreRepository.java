package com.lukamaletin.codeshare.repository;

import com.lukamaletin.codeshare.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    Optional<Score> findById(long id);

    List<Score> findAll();
}
