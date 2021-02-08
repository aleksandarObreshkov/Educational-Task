package com.example.backend.controllers;

import com.example.backend.annotations.*;
import com.example.backend.constants.HttpMethod;
import com.example.backend.constants.HttpStatus;
import com.example.backend.repositories.EntityRepository;
import com.example.backend.RESTEntities.ResponseEntity;
import model.Character;

import java.util.List;

@RequestPath(value = "/tomcat_backend_war_exploded/characters")
public class CharacterController {

    private final EntityRepository repository = new EntityRepository();

    @RequestMapping(method = HttpMethod.GET)
    public ResponseEntity<List<Character>> get() {
        List<Character> result = repository.findAll(Character.class);
        if (!result.isEmpty()) return new ResponseEntity<>(result, HttpStatus.OK);
        else return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = HttpMethod.GET)
    public ResponseEntity<Character> get(Long id){
        Character result = repository.findById(id, Character.class);
        if (result!=null) return new ResponseEntity<>(result,HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = HttpMethod.POST)
    public ResponseEntity<String> post(Character characterToAdd){
        repository.save(characterToAdd);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = HttpMethod.DELETE)
    public ResponseEntity<String> delete(Long id){
        boolean result = repository.deleteById(id, Character.class);
        if (result) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }



    
}
