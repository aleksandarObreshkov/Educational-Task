package com.example.backend.controllers;

import com.example.backend.annotations.*;
import com.example.backend.constants.HttpMethod;
import com.example.backend.constants.HttpStatus;
import com.example.backend.repositories.EntityRepository;
import com.example.backend.RESTEntities.ResponseEntity;
import model.Movie;

import java.util.List;
@RequestPath(value = "/tomcat_backend_war_exploded/movies")
public class MovieController{

    private final EntityRepository repository;

    public MovieController(EntityRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = HttpMethod.GET)
    public ResponseEntity<List<Movie>> get() {
        List<Movie> result = repository.findAll(Movie.class);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "/{id}", method = HttpMethod.GET)
    public ResponseEntity<Movie> get(@PathVariable("id") Long id){
        Movie result = repository.findById(id, Movie.class);
        if (result!=null) {
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = HttpMethod.POST)
    public ResponseEntity<String> post(@RequestBody Movie movieToAdd){
        repository.save(movieToAdd);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = HttpMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("id")Long id){
        boolean isDeleted = repository.deleteById(id, Movie.class);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
}
