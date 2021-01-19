package com.example.backend.controllers;

import com.example.backend.annotations.DeleteMapping;
import com.example.backend.annotations.GetMapping;
import com.example.backend.annotations.PostMapping;
import com.example.backend.annotations.RequestPath;
import com.example.backend.services.EntityService;
import com.example.backend.RESTEntities.ResponseEntity;
import model.Character;

import java.util.List;

@RequestPath(value = "/tomcat_war/characters")
public class CharacterController {

    private final EntityService service = new EntityService();

    public CharacterController() {
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Character>> get() {
        return service.getAll(Character.class);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Character> get(Long id){
        return service.getById(id, Character.class);
    }

    @PostMapping
    public ResponseEntity<String> post(Character characterToAdd){
        return service.add(characterToAdd);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(Long id){
        return service.deleteById(id, Character.class);
    }



    
}
