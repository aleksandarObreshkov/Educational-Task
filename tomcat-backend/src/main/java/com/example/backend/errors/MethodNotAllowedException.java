package com.example.backend.errors;

public class MethodNotAllowedException extends Throwable {

    private final String message;

    public MethodNotAllowedException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
