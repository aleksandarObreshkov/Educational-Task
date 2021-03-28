package com.example.controllers;

import com.example.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import com.example.repositories.EntityRepository;
import com.example.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController extends EntityController<Movie>{

    @Autowired
    public MovieController(MovieRepository repository) {
        super(repository);
    }
}
