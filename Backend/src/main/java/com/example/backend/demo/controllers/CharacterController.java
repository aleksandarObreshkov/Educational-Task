package com.example.backend.demo.controllers;

import com.example.backend.demo.repositories.EntityRepository;
import model.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/characters")
public class CharacterController {

    public EntityRepository repository;

    @Autowired
    public CharacterController(EntityRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public ResponseEntity<List<Character>> getCharacters(){
        List<Character> result = repository.findAll(Character.class);
        if (result!=null) return ResponseEntity.ok(result);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable Long id) {
        Character result = repository.findById(id, Character.class);
        if (result!=null) return ResponseEntity.ok(result);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCharacterById(@PathVariable Long id) {
        boolean isDeleted = repository.deleteById(id, Character.class);
        if (isDeleted) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<String> addCharacter(@Valid @RequestBody Character character) {
        repository.save(character);
        return ResponseEntity.noContent().build();
    }

}
