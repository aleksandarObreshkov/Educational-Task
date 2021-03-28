package com.example.backend.testcontrollers;

import com.example.rest_entities.ResponseEntity;
import com.example.annotations.RequestBody;
import com.example.annotations.RequestMapping;
import com.example.annotations.RequestPath;
import com.example.constants.HttpMethod;

@RequestPath(value = "/invalidBodyController")
public class InvalidBodyController {

    public InvalidBodyController() {
    }

    @RequestMapping(method = HttpMethod.POST)
    public ResponseEntity<String> postInvalidBody(@RequestBody Object entity) {
        throw new IllegalArgumentException("Invalid request body");
    }
}
