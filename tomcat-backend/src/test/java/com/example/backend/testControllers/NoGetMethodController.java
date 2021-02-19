package com.example.backend.testControllers;

import com.example.backend.RESTEntities.ResponseEntity;
import com.example.backend.annotations.PathVariable;
import com.example.backend.annotations.RequestBody;
import com.example.backend.annotations.RequestMapping;
import com.example.backend.annotations.RequestPath;
import com.example.backend.constants.HttpMethod;
import com.example.backend.constants.HttpStatus;

@RequestPath(value = "/tomcat_backend_war_exploded/noGetMethodController")
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
