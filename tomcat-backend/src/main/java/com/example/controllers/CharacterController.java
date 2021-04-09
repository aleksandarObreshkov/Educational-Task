package com.example.controllers;

import com.example.annotations.PathVariable;
import com.example.annotations.RequestBody;
import com.example.annotations.RequestMapping;
import com.example.annotations.RequestPath;
import com.example.constants.HttpMethod;
import com.example.constants.HttpStatus;
import com.example.repositories.CharacterRepository;
import com.example.model.Character;
import com.example.repositories.MovieRepository;
import com.example.rest_entities.ResponseEntity;
import com.example.services.CharacterService;
import com.example.services.MovieService;
import com.example.services.deletion.CharacterDeletionService;
import com.example.services.deletion.MovieDeletionService;

import java.util.List;

@RequestPath(value = "/characters")
public class CharacterController {

    private final CharacterRepository repository;
    private final CharacterService service;


    public CharacterController(CharacterRepository repository, CharacterService service) {
        this.repository=repository;
        this.service=service;
    }

    public CharacterController(){
        this(new CharacterRepository(), new CharacterService(new CharacterRepository(), new CharacterDeletionService()));
    }


    @RequestMapping(method = HttpMethod.GET)
    public ResponseEntity<List<Character>> get() {
        List<Character> result = repository.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = HttpMethod.GET)
    public ResponseEntity<Character> get(@PathVariable("id") Long id) {
        Character result = repository.findById(id);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = HttpMethod.POST)
    public ResponseEntity<String> post(@RequestBody Character objectToAdd) {
        service.save(objectToAdd);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = HttpMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        boolean result = service.deleteById(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
