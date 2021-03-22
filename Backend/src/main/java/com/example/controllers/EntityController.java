package com.example.controllers;

import com.example.repositories.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

public class EntityController {


    public final EntityRepository<Object> repository;

    @Autowired
    public EntityController(@Qualifier("entityRepository") EntityRepository<Object> repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public ResponseEntity<List<Object>> getAll(Class<Object> type){
        return ResponseEntity.ok(repository.findAll());
    }
}
