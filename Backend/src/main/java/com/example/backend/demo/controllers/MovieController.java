package com.example.backend.demo.controllers;

import com.example.backend.demo.repositories.EntityRepository;
import model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    public EntityRepository repository;

    @Autowired
    public MovieController(EntityRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public ResponseEntity<List<Movie>> getCharacters(){
        List<Movie> result = repository.findAll(Movie.class);
        if (result!=null) return ResponseEntity.ok(result);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getCharacterById(@PathVariable Long id) {
        Movie result = repository.findById(id, Movie.class);
        if (result!=null) return ResponseEntity.ok(result);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCharacterById(@PathVariable Long id) {
        boolean isDeleted = repository.deleteById(id, Movie.class);
        if (isDeleted) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<String> addCharacter(@Valid @RequestBody Movie movie) {
        repository.save(movie);
        return ResponseEntity.noContent().build();
    }


}
