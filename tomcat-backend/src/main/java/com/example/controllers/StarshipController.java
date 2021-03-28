package com.example.controllers;

import com.example.annotations.PathVariable;
import com.example.annotations.RequestBody;
import com.example.annotations.RequestMapping;
import com.example.annotations.RequestPath;
import com.example.constants.HttpMethod;
import com.example.constants.HttpStatus;
import com.example.repositories.StarshipRepository;
import com.example.model.Starship;
import com.example.rest_entities.ResponseEntity;

import java.util.List;


@RequestPath(value = "/starships")
public class StarshipController{

    private final StarshipRepository repository;

    public StarshipController(StarshipRepository repository) {
        this.repository = repository;
    }

    public StarshipController(){
        this(new StarshipRepository());
    }

    @RequestMapping(method = HttpMethod.GET)
    public ResponseEntity<List<Starship>> get() {
        List<Starship> result = repository.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = HttpMethod.GET)
    public ResponseEntity<Starship> get(@PathVariable("id") Long id) {
        Starship result = repository.findById(id);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = HttpMethod.POST)
    public ResponseEntity<String> post(@RequestBody Starship objectToAdd) {
        repository.save(objectToAdd);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = HttpMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        boolean result = repository.deleteById(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
