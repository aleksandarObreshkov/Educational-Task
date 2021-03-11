package com.example.backend.resolver.starship;

import graphql.kickstart.tools.GraphQLResolver;
import model.Starship;
import org.springframework.stereotype.Component;
import repositories.EntityRepository;

import java.util.Optional;

@Component
public class StarshipResolver implements GraphQLResolver<Starship> {

    private final EntityRepository repository;


    public StarshipResolver(EntityRepository repository) {
        this.repository = repository;
    }

    public Iterable<Starship> allStarships(){ return repository.findAll(Starship.class); }

    public Optional<Starship> starship(Long id){ return Optional.of(repository.findById(id, Starship.class)); }
}
