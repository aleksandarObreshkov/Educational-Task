package com.example.backend.RESTEntities;

import com.example.backend.constants.HttpMethod;

public class RequestEntity {

    private long id;
    private HttpMethod method;
    private Object body;
    private String uri;

    public RequestEntity(HttpMethod method) {
        this.method = method;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
