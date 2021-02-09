package com.example.backend.RESTEntities;

import com.example.backend.constants.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class RequestEntity {

    private final HttpMethod method;
    private Object body;
    private Map<String, String> pathVariables;

    public RequestEntity(HttpMethod method) {
        this.method = method;
        pathVariables=new HashMap<>();
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Map<String, String> getPathVariables() {
        return pathVariables;
    }

    public void setPathVariables(Map<String, String> pathVariables) {
        this.pathVariables = pathVariables;
    }
}
