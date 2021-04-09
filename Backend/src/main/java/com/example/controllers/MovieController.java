package com.example.controllers;

import com.example.model.Movie;
import com.example.services.MovieService;
import com.example.spring_data_repositories.MovieRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController extends EntityController<Movie, MovieService>{

    public MovieController(JpaRepository<Movie, Long> repository, MovieService service) {
        super(repository, service);
    }

    @Override
    @RequestMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        boolean isDeleted = service.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
