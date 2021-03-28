package com.example.backend.testcontrollers;

import com.example.rest_entities.ResponseEntity;
import com.example.annotations.PathVariable;
import com.example.annotations.RequestBody;
import com.example.annotations.RequestMapping;
import com.example.annotations.RequestPath;
import com.example.constants.HttpMethod;
import com.example.constants.HttpStatus;

@RequestPath(value = "/noGetMethodController")
public class NoGetMethodController {

    @RequestMapping(value = "/{id}", method = HttpMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = HttpMethod.POST)
    public ResponseEntity<String> post(@RequestBody Object entity) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
