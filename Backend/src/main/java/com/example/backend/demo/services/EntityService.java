package com.example.backend.demo.services;

import com.example.backend.demo.repositories.EntityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityService {

    private final EntityRepository repository;

    public EntityService(EntityRepository repository) {
        this.repository = repository;
    }

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
