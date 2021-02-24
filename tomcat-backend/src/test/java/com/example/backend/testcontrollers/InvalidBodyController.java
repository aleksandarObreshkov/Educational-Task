package com.example.backend.testcontrollers;

import com.example.backend.RESTEntities.ResponseEntity;
import com.example.backend.annotations.RequestBody;
import com.example.backend.annotations.RequestMapping;
import com.example.backend.annotations.RequestPath;
import com.example.backend.constants.HttpMethod;

@RequestPath(value = "/tomcat_backend_war_exploded/invalidBodyController")
public class InvalidBodyController {

    public InvalidBodyController() {
    }

    @RequestMapping(method = HttpMethod.POST)
    public ResponseEntity<String> postInvalidBody(@RequestBody Object entity) {
        throw new IllegalArgumentException("Invalid request body");
    }
}
