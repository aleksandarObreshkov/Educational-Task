package com.example.backend.RESTEntities;


import com.example.backend.constants.HttpStatus;
import com.fasterxml.jackson.annotation.JsonSetter;


import java.io.Serializable;

public class ResponseEntity<T> implements Serializable {

    private T body;
    private HttpStatus status;
    private String contentType;


    public ResponseEntity(T body, HttpStatus status) {
        this.body = body;
        this.status = status;

    }

    public T getBody() {
        return body;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @JsonSetter
    public void setBody(T body) {
        this.body = body;
    }

    @JsonSetter
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}

