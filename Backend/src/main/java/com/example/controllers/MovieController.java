package com.example.controllers;

import com.example.model.Movie;
import com.example.spring_data_repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController extends EntityController<Movie>{

    @Autowired
    public MovieController(MovieRepository repository) {
        super(repository);
    }

    @Override
    public ResponseEntity<String> deleteById(Long id) {
        MovieRepository movieRepository = (MovieRepository) repository;
        int deleted = movieRepository.deleteMovieById(id);
        if (deleted==1) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
