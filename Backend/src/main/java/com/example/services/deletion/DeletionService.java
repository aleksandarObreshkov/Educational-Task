package com.example.services.deletion;

import com.example.spring_data_repositories.CharacterRepository;

public abstract class DeletionService<T> {

    protected CharacterRepository repository;

    public DeletionService(CharacterRepository repository) {
        this.repository = repository;
    }

    public abstract void unlink(T entity);
}
