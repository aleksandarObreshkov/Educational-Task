package com.example.backend.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import repositories.EntityRepository;
import model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    public final EntityRepository repository;

    @Autowired
    public MovieController(@Qualifier("entityRepository")EntityRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public ResponseEntity<List<Movie>> getAllMovies(){
        List<Movie> result = repository.findAll(Movie.class);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Movie result = repository.findById(id, Movie.class);
        if (result!=null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovieById(@PathVariable Long id) {
        boolean isDeleted = repository.deleteById(id, Movie.class);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<String> addMovie(@Valid @RequestBody Movie movie) {
        repository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
