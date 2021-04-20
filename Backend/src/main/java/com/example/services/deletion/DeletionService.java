package com.example.services.deletion;

import com.example.repositories.spring.CharacterRepository;

public abstract class DeletionService<T> {

    protected CharacterRepository repository;

    public DeletionService(CharacterRepository repository) {
        this.repository = repository;
    }

    public abstract void unlink(T entity);
}
