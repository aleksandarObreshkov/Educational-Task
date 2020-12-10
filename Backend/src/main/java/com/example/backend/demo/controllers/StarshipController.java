package com.example.backend.demo.controllers;

import model.Starship;
import com.example.backend.demo.services.StarshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/starships")
public class StarshipController {

    private final StarshipService service;

    @Autowired
    public StarshipController(StarshipService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<Starship>> getStarships() throws IOException {
        return ResponseEntity.status(200).body(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Starship> getStarshipById(@PathVariable String id) throws IOException {
        return ResponseEntity.status(200).body(service.getStarshipById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStarshipById(@PathVariable String id) throws IOException {
        service.deleteStarshipById(id);
        return ResponseEntity.status(200).body("Starship deleted successfully.");
    }

    @PostMapping("")
    public ResponseEntity<String> addStarship(@Valid @RequestBody Starship starship) throws IOException{
        service.addStarship(starship);
        return ResponseEntity.status(200).body("Starship added successfully.");
    }
}
