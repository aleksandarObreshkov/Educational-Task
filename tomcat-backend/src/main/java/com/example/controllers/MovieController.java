package com.example.controllers;

import com.example.annotations.PathVariable;
import com.example.annotations.RequestBody;
import com.example.annotations.RequestMapping;
import com.example.annotations.RequestPath;
import com.example.constants.HttpMethod;
import com.example.constants.HttpStatus;
import com.example.repositories.CharacterRepository;
import com.example.repositories.MovieRepository;
import com.example.model.Movie;
import com.example.rest_entities.ResponseEntity;
import com.example.services.MovieService;
import com.example.services.deletion.MovieDeletionService;

import java.util.List;


@RequestPath(value = "/movies")
public class MovieController {

    private final MovieRepository repository;
    private final MovieService service;

    public MovieController(MovieRepository repository, MovieService service) {
        this.repository=repository;
        this.service=service;
    }

    public MovieController(){
        this(new MovieRepository(), new MovieService(new MovieRepository(), new MovieDeletionService()));
    }


    @RequestMapping(method = HttpMethod.GET)
    public ResponseEntity<List<Movie>> get() {
        List<Movie> result = repository.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = HttpMethod.GET)
    public ResponseEntity<Movie> get(@PathVariable("id") Long id) {
        Movie result = repository.findById(id);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = HttpMethod.POST)
    public ResponseEntity<String> post(@RequestBody Movie objectToAdd) {
        repository.save(objectToAdd);
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
