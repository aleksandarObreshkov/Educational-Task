package com.example.resolver.movie;

import com.example.errors.NotFoundException;
import graphql.kickstart.tools.GraphQLResolver;
import com.example.model.Movie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.example.repositories.EntityRepository;

import java.util.Optional;

@Component
// TODO This resolver is also very similar to HumanResolver and DroidResolver. Remove the duplication.
public class MovieResolver implements GraphQLResolver<Movie> {

    private final EntityRepository<Movie> repository;

    public MovieResolver(@Qualifier("entityRepository") EntityRepository<Movie> repository) {
        this.repository = repository;
    }

    public Iterable<Movie> allMovies() {
        return repository.findAll();
    }

    public Optional<Movie> movie(Long id) {
        Movie movieToReturn = repository.findById(id);
        if (movieToReturn == null) {
            throw new NotFoundException("No Movie with the specified id.");
        }
        return Optional.of(movieToReturn);
    }

}
