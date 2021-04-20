package com.example.controllers;

import com.example.model.Movie;
import com.example.services.MovieService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController extends EntityController<Movie, MovieService>{

    public MovieController(MovieService service) {
        super(service);
    }
}
