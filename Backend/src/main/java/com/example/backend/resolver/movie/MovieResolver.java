package com.example.backend.resolver.movie;

import graphql.kickstart.tools.GraphQLResolver;
import model.Movie;
import org.springframework.stereotype.Component;
import repositories.EntityRepository;

import java.util.Optional;

@Component
public class MovieResolver implements GraphQLResolver<Movie> {

    private final EntityRepository repository;

    public MovieResolver(EntityRepository repository) {
        this.repository = repository;
    }

    public Iterable<Movie> allMovies(){
        return repository.findAll(model.Movie.class);
    }

    public Optional<Movie> movie(Long id){
        return Optional.of(repository.findById(id, Movie.class));
    }
}
