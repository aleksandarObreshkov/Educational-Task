package com.example.services;

import com.example.repositories.EntityRepository;
import com.example.services.deletion.DeletionService;

import java.util.List;

public abstract class EntityService<T, R extends EntityRepository<T>, S extends DeletionService<T>> {

    protected R repository;
    protected S deletionService;

    public EntityService(R repository, S deletionService) {
        this.repository = repository;
        this.deletionService = deletionService;
    }

    public boolean deleteById(Long id){
        T entityToDelete = repository.findById(id);
        if (entityToDelete == null){
            return false;
        }
        deletionService.unlink(entityToDelete);
        repository.deleteById(id);
        //Im returning true since if the entity didnt exist, it would have been caught when
        //checking the Optional
        //If something happens during the transaction, it will be send directly to the user as an error
        return true;
    }

    public T findById(Long id){
        return repository.findById(id);
    }

    public List<T> findAll(){
        return repository.findAll();
    }

    public void save(T entity){
        repository.save(entity);
    }
}
