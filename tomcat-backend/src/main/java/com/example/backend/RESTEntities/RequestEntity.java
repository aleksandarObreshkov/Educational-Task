package com.example.backend.RESTEntities;

import com.example.backend.constants.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class RequestEntity {

    private final HttpMethod method;
    private String body;
    private Map<String, String> pathVariables;
    private final String requestURI;

    public RequestEntity(HttpMethod method, String requestURI) {
        this.method = method;
        this.requestURI = requestURI;
        pathVariables=new HashMap<>();
    }

    public HttpMethod getMethod() {
        return method;
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

    public String getRequestURI() {
        return requestURI;
    }
}
