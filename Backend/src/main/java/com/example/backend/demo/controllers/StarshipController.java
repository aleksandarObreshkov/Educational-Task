package com.example.backend.demo.controllers;

import com.example.backend.demo.services.EntityService;
import model.Starship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/starships")
public class StarshipController {

    private final EntityService service;

    @Autowired
    public StarshipController(EntityService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<Starship>> getStarships() {
        return service.getAll(Starship.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Starship> getStarshipById(@PathVariable Long id) {
        return service.getById(id, Starship.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStarshipById(@PathVariable Long id) {
        return service.deleteById(id, Starship.class);
    }

    @PostMapping("")
    public ResponseEntity<String> addStarship(@Valid @RequestBody Starship starship) {
        return service.add(starship);
    }
}
