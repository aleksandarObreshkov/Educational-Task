package com.example.services.deletion;

import com.example.repositories.CharacterRepository;

public abstract class DeletionService<T> {

    protected final CharacterRepository repository;

    public DeletionService(CharacterRepository repository) {
        this.repository = repository;
    }
    public DeletionService(){
        this(new CharacterRepository());
    }

    public abstract void unlink(T entity);
}
