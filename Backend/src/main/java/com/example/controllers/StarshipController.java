package com.example.controllers;

import com.example.model.Starship;
import com.example.spring_data_repositories.StarshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/starships")
public class StarshipController extends EntityController<Starship>{

    @Autowired
    public StarshipController(StarshipRepository repository){
        super(repository);
    }

    @Override
    public ResponseEntity<String> deleteById(Long id) {
        StarshipRepository starshipRepository = (StarshipRepository) repository;
        Starship deleted = starshipRepository.deleteStarshipById(id);
        if (deleted!=null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
