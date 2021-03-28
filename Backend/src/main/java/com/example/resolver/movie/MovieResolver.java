package com.example.resolver.movie;

import com.example.repositories.MovieRepository;
import com.example.resolver.EntityResolver;
import com.example.model.Movie;
import org.springframework.stereotype.Component;


@Component
public class MovieResolver extends EntityResolver<Movie, MovieRepository>{

    public MovieResolver(MovieRepository repository) {
        super(repository);
    }

    public MovieResolver() {
        this(new MovieRepository());
    }
}
