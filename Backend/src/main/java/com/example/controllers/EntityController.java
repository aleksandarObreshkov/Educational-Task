package com.example.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public abstract class EntityController<T> {

    public final JpaRepository<T, Long> repository;

    public EntityController(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<T>> getAll() {
        List<T> result = repository.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable Long id) {
        Optional<T> result = repository.findById(id);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public abstract ResponseEntity<String> deleteById(@PathVariable Long id);

    @PostMapping
    public ResponseEntity<String> add(@Valid @RequestBody T entity) {
        repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
