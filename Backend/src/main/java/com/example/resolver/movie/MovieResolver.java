package com.example.resolver.movie;

import com.example.spring_data_repositories.MovieRepository;
import com.example.resolver.EntityResolver;
import com.example.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieResolver extends EntityResolver<Movie, MovieRepository>{

    @Autowired
    public MovieResolver(MovieRepository repository) {
        super(repository);
    }

}
