package com.example.backend.rest_entities;

import java.util.HashMap;
import java.util.Map;

public class RequestEntity {

    private String body;
    private Map<String, String> pathVariables;

    public RequestEntity() {
        pathVariables=new HashMap<>();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getPathVariables() {
        return pathVariables;
    }

    public void setPathVariables(Map<String, String> pathVariables) {
        this.pathVariables = pathVariables;
    }

}