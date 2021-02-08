package com.example.backend.controllers;

import com.example.backend.annotations.*;
import com.example.backend.constants.HttpMethod;
import com.example.backend.constants.HttpStatus;
import com.example.backend.repositories.EntityRepository;
import com.example.backend.RESTEntities.ResponseEntity;
import model.Starship;

import java.util.List;

@RequestPath(value = "/tomcat_backend_war_exploded/movies")
public class StarshipController {

    private final EntityRepository repository = new EntityRepository();

    @RequestMapping(method = HttpMethod.GET)
    public ResponseEntity<List<Starship>> get() {
        List<Starship> result = repository.findAll(Starship.class);
        if (!result.isEmpty()) return new ResponseEntity<>(result, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = HttpMethod.GET)
    public ResponseEntity<Starship> get(Long id){
        Starship result = repository.findById(id, Starship.class);
        if (result!=null) return new ResponseEntity<>(result,HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = HttpMethod.POST)
    public ResponseEntity<String> post(Starship starshipToAdd){
        repository.save(starshipToAdd);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = HttpMethod.DELETE)
    public ResponseEntity<String> delete(Long id){
        boolean isDeleted = repository.deleteById(id, Starship.class);
        if (isDeleted) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
