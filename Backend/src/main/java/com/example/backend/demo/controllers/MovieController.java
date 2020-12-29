package com.example.backend.demo.controllers;

import com.example.backend.demo.services.EntityService;
import model.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final EntityService service;

    public MovieController(EntityService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<Movie>>getMovies() {
        return service.getAll(Movie.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return service.getById(id, Movie.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        return service.deleteById(id, Movie.class);
    }

    @PostMapping("")
    public ResponseEntity<String> addMovie(@Valid @RequestBody Movie movie) {
        return service.add(movie);
    }


}
