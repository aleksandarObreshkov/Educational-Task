package com.example.backend.services;

import com.example.backend.repositories.EntityRepository;
import com.example.backend.RESTEntities.ResponseEntity;

import java.util.List;

public class EntityService {

    private final EntityRepository repository = new EntityRepository();

    public <T> ResponseEntity<List<T>> getAll(Class<T> type){
        return repository.findAll(type);
    }

    public <T> ResponseEntity<T> getById(Long id, Class<T> type){
        return repository.findById(id, type);
    }

    public <T> ResponseEntity<String> deleteById(Long id, Class<T> type){
        return repository.deleteById(id, type);
    }

    public <T> ResponseEntity<String> add(T objectToPersists){
        return repository.save(objectToPersists);
    }
}
