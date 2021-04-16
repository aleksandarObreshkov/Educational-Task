package com.example.controllers;

import com.example.model.Character;
import com.example.services.CharacterService;
import com.example.spring_data_repositories.CharacterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("characters")
public class CharacterController extends EntityController<Character, CharacterService>{

    public CharacterController(CharacterRepository repository, CharacterService service) {
        super(repository, service);
    }

    // TODO This method is repeated in all controllers. Why not move it to EntityController?
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        boolean deleted = service.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // TODO Why override the method in EntityController?
    @Override
    @PostMapping("")
    public ResponseEntity<String> add(@Valid @RequestBody Character entity) {
        service.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
