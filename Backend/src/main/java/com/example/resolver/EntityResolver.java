package com.example.resolver;

import com.example.errors.NotFoundException;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public abstract class EntityResolver<T, R extends JpaRepository<T, Long>> implements GraphQLResolver<T> {

    protected final R repository;

    public EntityResolver(R repository) {
        this.repository = repository;
    }

    public Iterable<T> all(){
        return repository.findAll();
    }

    public Optional<T> entityWithId(Long id){
        Optional<T> objectToReturn = repository.findById(id);
        if (objectToReturn.isEmpty()) {
            throw new NotFoundException("No entity with the specified id.");
        }
        return objectToReturn;
    }
}
