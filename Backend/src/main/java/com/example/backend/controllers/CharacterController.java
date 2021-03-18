package com.example.backend.controllers;

import model.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repositories.EntityRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    public final EntityRepository repository;

    @Autowired
    public CharacterController(@Qualifier("entityRepository") EntityRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Character>> getAllCharacters() {
        List<Character> result = repository.findAll(Character.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable Long id) {
        Character result = repository.findById(id, Character.class);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCharacterById(@PathVariable Long id) {
        boolean isDeleted = repository.deleteById(id, Character.class);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> addCharacter(@Valid @RequestBody Character character) {
        repository.save(character);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
