package com.example.services;

import com.example.services.deletion.DeletionService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class EntityService<T, R extends JpaRepository<T, Long>, S extends DeletionService<T>> {

    protected R repository;
    protected S deletionService;

    public EntityService(R repository, S deletionService) {
        this.repository = repository;
        this.deletionService = deletionService;
    }

    public boolean deleteById(Long id){
        Optional<T> optionalEntity = repository.findById(id);
        if (optionalEntity.isEmpty()){
            return false;
        }
        T entityToDelete = optionalEntity.get();
        deletionService.unlink(entityToDelete);
        repository.save(entityToDelete);
        repository.deleteById(id);
        return true;
    }

    public T save(T entity){
        return repository.save(entity);
    }

    public List<T> findAll(){
        return repository.findAll();
    }

    public Optional<T> findById(Long id){
        return repository.findById(id);
    }
}
