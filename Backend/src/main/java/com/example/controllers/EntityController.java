package com.example.controllers;

import com.example.repositories.EntityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

public abstract class EntityController<T> {

    public final EntityRepository<T> repository;

    public EntityController(EntityRepository<T> repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<T>> getAll() {
        List<T> result = repository.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable Long id) {
        T result = repository.findById(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        boolean isDeleted = repository.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> add(@Valid @RequestBody T entity) {
        repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
