package com.example.resolver;

import com.example.errors.NotFoundException;
import com.example.repositories.EntityRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

public abstract class EntityResolver<T, R extends EntityRepository<T>> implements GraphQLResolver<T> {

    protected final R repository;

    public EntityResolver(R repository) {
        this.repository = repository;
    }

    public Iterable<T> all(){
        return repository.findAll();
    }

    public Optional<T> entityWithId(Long id){
        T objectToReturn = repository.findById(id);
        if (objectToReturn == null) {
            throw new NotFoundException("No entity with the specified id.");
        }
        return Optional.of(objectToReturn);
    }
}
