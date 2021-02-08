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

    private final EntityRepository repository = new EntityRepository();

    @RequestMapping(method = HttpMethod.GET)
    public ResponseEntity<List<Movie>> get() {
        List<Movie> result = repository.findAll(Movie.class);
        if (!result.isEmpty()) return new ResponseEntity<>(result, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = HttpMethod.GET)
    public ResponseEntity<Movie> get(Long id){
        Movie result = repository.findById(id, Movie.class);
        if (result!=null) return new ResponseEntity<>(result,HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = HttpMethod.POST)
    public ResponseEntity<String> post(Movie starshipToAdd){
        repository.save(starshipToAdd);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = HttpMethod.DELETE)
    public ResponseEntity<String> delete(Long id){
        boolean isDeleted = repository.deleteById(id, Movie.class);
        if (isDeleted) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    
}
