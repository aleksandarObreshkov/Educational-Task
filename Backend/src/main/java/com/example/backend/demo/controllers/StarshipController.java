package com.example.backend.demo.controllers;

import repositories.EntityRepository;
import model.Starship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/starships")
public class StarshipController {

    public final EntityRepository repository;

    @Autowired
    public StarshipController(EntityRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public ResponseEntity<List<Starship>> getCharacters(){
        List<Starship> result = repository.findAll(Starship.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Starship> getCharacterById(@PathVariable Long id) {
        Starship result = repository.findById(id, Starship.class);
        if (result!=null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCharacterById(@PathVariable Long id) {
        boolean isDeleted = repository.deleteById(id, Starship.class);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<String> addCharacter(@Valid @RequestBody Starship starship) {
        repository.save(starship);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
