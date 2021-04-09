package com.example.controllers;

import com.example.model.Starship;
import com.example.services.StarshipService;
import com.example.services.deletion.StarshipDeletionService;
import com.example.spring_data_repositories.StarshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/starships")
public class StarshipController extends EntityController<Starship, StarshipService> {

    @Autowired
    public StarshipController(StarshipRepository repository, StarshipService service){
        super(repository, service);
    }

    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        boolean deleted = service.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
