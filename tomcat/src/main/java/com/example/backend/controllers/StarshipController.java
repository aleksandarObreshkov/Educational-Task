package com.example.backend.controllers;

import com.example.backend.annotations.DeleteMapping;
import com.example.backend.annotations.GetMapping;
import com.example.backend.annotations.PostMapping;
import com.example.backend.annotations.RequestPath;
import com.example.backend.services.EntityService;
import com.example.backend.RESTEntities.ResponseEntity;
import model.Starship;

import java.util.List;

@RequestPath(value = "/tomcat_war/starships")
public class StarshipController {

    private final EntityService service = new EntityService();

    public StarshipController() {
    }

    @GetMapping
    public ResponseEntity<List<Starship>> get() {
        return service.getAll(Starship.class);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Starship> get(Long id){
        return service.getById(id, Starship.class);
    }

    @PostMapping
    public ResponseEntity<String> post(Starship starshipToAdd){
        return service.add(starshipToAdd);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(Long id){
        return service.deleteById(id, Starship.class);
    }
}
