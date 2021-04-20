package com.example.controllers;

import com.example.model.Character;
import com.example.services.CharacterService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("characters")
public class CharacterController extends EntityController<Character, CharacterService>{

    public CharacterController(CharacterService service) {
        super(service);
    }
}
