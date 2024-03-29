package com.example.controllers;

import com.example.services.StarshipService;
import com.example.annotations.PathVariable;
import com.example.annotations.RequestBody;
import com.example.annotations.RequestMapping;
import com.example.annotations.RequestPath;
import com.example.constants.HttpMethod;
import com.example.constants.HttpStatus;
import com.example.services.deletion.StarshipDeletionService;
import com.example.repositories.StarshipRepository;
import com.example.model.Starship;
import com.example.rest.entities.ResponseEntity;

import java.util.List;

@RequestPath(value = "/starships")
public class StarshipController{

    private final StarshipService service;


    public StarshipController(StarshipService service) {
        this.service = service;
    }

    public StarshipController(){
        this(new StarshipService(new StarshipRepository(), new StarshipDeletionService()));
    }

    @RequestMapping(method = HttpMethod.GET)
    public ResponseEntity<List<Starship>> get() {
        List<Starship> result = service.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = HttpMethod.GET)
    public ResponseEntity<Starship> get(@PathVariable("id") Long id) {
        Starship result = service.findById(id);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = HttpMethod.POST)
    public ResponseEntity<String> post(@RequestBody Starship objectToAdd) {
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
