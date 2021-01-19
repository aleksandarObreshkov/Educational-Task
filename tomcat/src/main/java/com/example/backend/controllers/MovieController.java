package com.example.backend.controllers;

import com.example.backend.annotations.DeleteMapping;
import com.example.backend.annotations.GetMapping;
import com.example.backend.annotations.PostMapping;
import com.example.backend.annotations.RequestPath;
import com.example.backend.services.EntityService;
import com.example.backend.RESTEntities.ResponseEntity;
import model.Movie;

import java.util.List;
@RequestPath(value = "/tomcat_war/movies")
public class MovieController{

    private final EntityService service = new EntityService();

    public MovieController() {
    }

    @GetMapping("")
    public ResponseEntity<List<Movie>> get() {
        return service.getAll(Movie.class);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Movie> get(Long id){
        return service.getById(id, Movie.class);
    }

    @PostMapping
    public <T> ResponseEntity<String> post(T movieToAdd){
        return service.add(movieToAdd);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(Long id){
        return service.deleteById(id, Movie.class);
    }
    
}
