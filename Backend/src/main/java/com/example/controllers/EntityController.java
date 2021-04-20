package com.example.controllers;

import com.example.services.EntityService;
import com.example.services.deletion.DeletionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public abstract class EntityController<T, S extends EntityService<T, ? extends JpaRepository<T, Long>, ? extends DeletionService<T>>> {

    public final S service;

    public EntityController(S service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<T>> getAll() {
        List<T> result = service.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable Long id) {
        Optional<T> result = service.findById(id);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        boolean deleted = service.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> add(@Valid @RequestBody T entity) {
        service.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
