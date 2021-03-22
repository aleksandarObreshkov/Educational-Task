package com.example.backend.rest_entities;

import com.example.backend.constants.HttpStatus;
import java.io.Serializable;

// TODO Add a serialVersionUid constant.
public class ResponseEntity<T> implements Serializable {

    private final T body;
    private final HttpStatus status;

    public ResponseEntity(T body, HttpStatus status) {
        this.body = body;
        this.status = status;
    }

    public ResponseEntity(HttpStatus status) {
        this.status = status;
        this.body = null;
    }

    public T getBody() {
        return body;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
