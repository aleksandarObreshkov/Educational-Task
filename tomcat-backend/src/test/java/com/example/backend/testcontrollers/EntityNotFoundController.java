package com.example.backend.testcontrollers;

import com.example.rest.entities.ResponseEntity;
import com.example.annotations.PathVariable;
import com.example.annotations.RequestMapping;
import com.example.annotations.RequestPath;
import com.example.constants.HttpMethod;
import com.example.constants.HttpStatus;

@RequestPath(value = "/notFoundController")
public class EntityNotFoundController {

    public EntityNotFoundController(){
    }

    @RequestMapping(value = "/{id}", method = HttpMethod.GET)
    public ResponseEntity<Object> getEntityNotFound(@PathVariable("id") Long id){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}", method = HttpMethod.DELETE)
    public ResponseEntity<String> deleteEntityNotFound(@PathVariable("id") Long id){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
