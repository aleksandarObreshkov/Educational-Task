package com.example.backend.demo.controllers;

import com.example.backend.demo.services.MovieService;
import model.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<Movie>>getMovies() throws IOException{
        return ResponseEntity.status(200).body(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String id) throws IOException {
        return ResponseEntity.status(200).body(service.getMovieById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable String id) throws IOException{
        service.deleteMovieById(id);
        return ResponseEntity.status(200).body("Movie deleted successfully.");
    }

    @PostMapping("")
    public ResponseEntity<String> addMovie(@Valid @RequestBody Movie movie) throws IOException{
        service.addMovie(movie);
        return ResponseEntity.status(200).body("Movie added successfully.");
    }


}
