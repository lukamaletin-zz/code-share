package com.lukamaletin.codeshare.controller;

import com.lukamaletin.codeshare.controller.exception.BadRequestException;
import com.lukamaletin.codeshare.controller.exception.NotFoundException;
import com.lukamaletin.codeshare.model.Language;
import com.lukamaletin.codeshare.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/languages")
public class LanguageController {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageController(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity findById(@PathVariable long id) {
        final Language language = languageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Language not found!"));
        return new ResponseEntity<>(language, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(languageRepository.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity create(@RequestBody Language language) {
        if (languageRepository.findByLanguageName(language.getLanguageName()).isPresent()) {
            throw new BadRequestException("Language name taken!");
        }
        languageRepository.save(language);
        return new ResponseEntity<>(language.getId(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity remove(@PathVariable long id) {
        languageRepository.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
