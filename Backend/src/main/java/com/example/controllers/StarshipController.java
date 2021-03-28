package com.example.controllers;

import com.example.repositories.StarshipRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import com.example.repositories.EntityRepository;
import com.example.model.Starship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/starships")
public class StarshipController extends EntityController<Starship>{

    public StarshipController() {
        this(new StarshipRepository());
    }

    public StarshipController(EntityRepository<Starship> repository){
        super(repository);
    }

}
