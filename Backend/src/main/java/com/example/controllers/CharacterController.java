package com.example.controllers;

import com.example.model.Character;
import com.example.spring_data_repositories.CharacterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("characters")
public class CharacterController extends EntityController<Character>{

    public CharacterController(CharacterRepository repository) {
        super(repository);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        CharacterRepository characterRepository = (CharacterRepository) repository;
        int deleted = characterRepository.deleteCharacterById(id);
        if (deleted==1) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
