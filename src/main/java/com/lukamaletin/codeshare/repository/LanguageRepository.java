package com.lukamaletin.codeshare.repository;

import com.lukamaletin.codeshare.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    Optional<Language> findById(long id);

    List<Language> findAll();

    Optional<Language> findByLanguageName(String languageName);
}
