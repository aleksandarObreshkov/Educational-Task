package com.example.backend.demo.controllers;

import com.example.backend.demo.services.EntityService;
import model.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/characters")
public class CharacterController {

    public EntityService service;

    @Autowired
    public CharacterController(EntityService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<Character>> getCharacters(){
        return service.getAll(Character.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable Long id) {
        return service.getById(id, Character.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCharacterById(@PathVariable Long id) {
        return service.deleteById(id, Character.class);
    }

    @PostMapping("")
    public ResponseEntity<String> addCharacter(@Valid @RequestBody Character character) {
        return service.add(character);
    }






}
