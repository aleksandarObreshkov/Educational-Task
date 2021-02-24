package com.example.backend.testcontrollers;

import com.example.backend.RESTEntities.ResponseEntity;
import com.example.backend.annotations.PathVariable;
import com.example.backend.annotations.RequestMapping;
import com.example.backend.annotations.RequestPath;
import com.example.backend.constants.HttpMethod;
import com.example.backend.constants.HttpStatus;

@RequestPath(value = "/tomcat_backend_war_exploded/notFoundController")
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
