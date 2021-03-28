package com.example.repositories;

import com.example.model.Movie;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepository extends EntityRepository<Movie> {

    public MovieRepository() {
        super(Movie.class);
    }
}
