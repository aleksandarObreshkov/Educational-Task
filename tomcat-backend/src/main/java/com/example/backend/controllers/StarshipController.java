package com.example.backend.controllers;

import com.example.backend.annotations.*;
import com.example.backend.constants.HttpMethod;
import com.example.backend.constants.HttpStatus;
import com.example.backend.repositories.EntityRepository;
import com.example.backend.RESTEntities.ResponseEntity;
import model.Starship;
import java.util.List;

@RequestPath(value = "/tomcat_backend_war_exploded/starships")
public class StarshipController {

    private final EntityRepository repository;

    public StarshipController(EntityRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = HttpMethod.GET)
    public ResponseEntity<List<Starship>> get() {
        List<Starship> result = repository.findAll(Starship.class);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = HttpMethod.GET)
    public ResponseEntity<Starship> get(@PathVariable("id") Long id){
        Starship result = repository.findById(id, Starship.class);
        if (result!=null) {
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = HttpMethod.POST)
    public ResponseEntity<String> post(@RequestBody Starship starshipToAdd){
        repository.save(starshipToAdd);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = HttpMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        boolean isDeleted = repository.deleteById(id, Starship.class);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
