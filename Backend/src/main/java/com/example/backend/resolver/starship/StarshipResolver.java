package com.example.backend.resolver.starship;

import com.example.backend.errors.NotFoundException;
import graphql.kickstart.tools.GraphQLResolver;
import model.Starship;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import repositories.EntityRepository;

import java.util.Optional;

@Component
public class StarshipResolver implements GraphQLResolver<Starship> {

    private final EntityRepository repository;


    public StarshipResolver(@Qualifier("entityRepository") EntityRepository repository) {
        this.repository = repository;
    }

    public Iterable<Starship> allStarships(){ return repository.findAll(Starship.class); }

    public Optional<Starship> starship(Long id) {
        Starship starshipToReturn = repository.findById(id, Starship.class);
        if (starshipToReturn == null) {
            throw new NotFoundException("No Starship with the specified id.");
        }
        return Optional.of(starshipToReturn);
    }
}
