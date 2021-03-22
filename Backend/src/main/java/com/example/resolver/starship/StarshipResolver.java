package com.example.resolver.starship;

import com.example.errors.NotFoundException;
import graphql.kickstart.tools.GraphQLResolver;
import com.example.model.Starship;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.example.repositories.EntityRepository;

import java.util.Optional;

@Component
// TODO
public class StarshipResolver implements GraphQLResolver<Starship> {

    private final EntityRepository<Starship> repository;

    public StarshipResolver(@Qualifier("entityRepository") EntityRepository<Starship> repository) {
        this.repository = repository;
    }

    public Iterable<Starship> allStarships() {
        return repository.findAll();
    }

    public Optional<Starship> starship(Long id) {
        Starship starshipToReturn = repository.findById(id);
        if (starshipToReturn == null) {
            throw new NotFoundException("No Starship with the specified id.");
        }
        return Optional.of(starshipToReturn);
    }

}
