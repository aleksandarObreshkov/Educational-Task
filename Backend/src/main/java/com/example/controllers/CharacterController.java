package com.example.controllers;

import com.example.model.Character;
import com.example.repositories.CharacterRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("characters")
public class CharacterController extends EntityController<Character>{

    public CharacterController() {
        this(new CharacterRepository<>(Character.class));
    }
    public CharacterController(CharacterRepository<Character> repository) {
        super(repository);
    }
}
