package com.example.controllers;

import com.example.model.Starship;
import com.example.services.StarshipService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/starships")
public class StarshipController extends EntityController<Starship, StarshipService> {

    public StarshipController(StarshipService service){
        super(service);
    }
}
