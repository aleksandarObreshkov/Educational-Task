package com.example.backend;

import org.springframework.http.ResponseEntity;
import java.util.List;

public class EntityService {

    private final EntityRepository repository = new EntityRepository();

    public String testMethod(){return repository.getTest();}

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
