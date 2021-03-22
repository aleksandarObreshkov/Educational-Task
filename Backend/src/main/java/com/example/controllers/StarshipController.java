package com.example.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import com.example.repositories.EntityRepository;
import com.example.model.Starship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/starships")
public class StarshipController {

    public final EntityRepository<Starship> repository;

    @Autowired
    public StarshipController(@Qualifier("entityRepository") EntityRepository<Starship> repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Starship>> getAllStarships() {
        List<Starship> result = repository.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Starship> getStarshipById(@PathVariable Long id) {
        Starship result = repository.findById(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStarshipById(@PathVariable Long id) {
        boolean isDeleted = repository.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> addStarship(@Valid @RequestBody Starship starship) {
        repository.save(starship);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
