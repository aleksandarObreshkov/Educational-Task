package com.example.backend.demo.controllers;

import model.Character;
import com.example.backend.demo.services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

import javax.validation.Valid;
import java.io.IOException;

import java.util.List;


@RestController
@RequestMapping("/characters")
public class CharacterController extends ResponseStatusExceptionResolver {

    public CharacterService service;

    @Autowired
    public CharacterController(CharacterService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<Character>> getCharacters() throws IOException{
        return ResponseEntity.status(200).body(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable Long id) throws IOException{
        return ResponseEntity.status(200).body(service.getCharacterById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCharacterById(@PathVariable Long id) throws IOException{
        service.deleteCharacterById(id);
        return ResponseEntity.status(200).body("Character deleted successfully.");
    }

    @PostMapping("")
    public ResponseEntity<String> addCharacter(@Valid @RequestBody Character character) throws IOException{
        service.addCharacter(character);
        return ResponseEntity.status(200).body("Character added successfully.");
    }






}
