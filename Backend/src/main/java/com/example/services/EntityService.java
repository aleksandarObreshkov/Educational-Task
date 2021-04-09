package com.example.services;

import com.example.services.deletion.DeletionService;
import org.springframework.data.jpa.repository.JpaRepository;

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
        //Im returning true since if the entity didnt exist, it would have been caught when
        //checking the Optional
        //If something happens during the transaction, it will be send directly to the user as an error
        return true;
    }
}
