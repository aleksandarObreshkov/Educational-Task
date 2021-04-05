package com.example.repositories;

import com.example.model.Movie;

public class MovieRepository extends EntityRepository<Movie> {
    public MovieRepository() {
        super(Movie.class);
    }
}
