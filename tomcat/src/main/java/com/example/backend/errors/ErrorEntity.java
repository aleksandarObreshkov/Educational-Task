package com.example.backend.errors;

import com.example.backend.constants.HttpStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ErrorEntity {
    private String message;
    @JsonIgnore
    private HttpStatus status;
    private Exception exception;

    public ErrorEntity(String message, HttpStatus status, Exception e) {
        this.message = message;
        this.status = status;
        this.exception=e;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
