package com.example.backend.controllers;

import com.example.backend.annotations.*;
import com.example.backend.constants.HttpMethod;
import com.example.backend.constants.HttpStatus;
import repositories.EntityRepository;
import com.example.backend.RESTEntities.ResponseEntity;
import model.Character;

import java.util.List;

@RequestPath(value = "/tomcat_backend_war_exploded/characters")
public class CharacterController {

    private final EntityRepository repository;

    public CharacterController(){
        this.repository=new EntityRepository();
    }

    public CharacterController(EntityRepository repository){
        this.repository = repository;
    }

    @RequestMapping(method = HttpMethod.GET)
    public ResponseEntity<List<Character>> get() {
        List<Character> result = repository.findAll(Character.class);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}", method = HttpMethod.GET)
    public ResponseEntity<Character> get(@PathVariable("id") Long id){
        Character result = repository.findById(id, Character.class);
        if (result!=null) {
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = HttpMethod.POST)
    public ResponseEntity<String> post(@RequestBody Character characterToAdd){
        repository.save(characterToAdd);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = HttpMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        boolean result = repository.deleteById(id, Character.class);
        if (result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    
}
