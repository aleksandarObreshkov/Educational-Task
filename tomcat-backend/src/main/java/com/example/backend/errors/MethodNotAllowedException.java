package com.example.backend.errors;

// TODO Add a serialVersionUid constant.
public class MethodNotAllowedException extends Throwable {

    private final String message;

    public MethodNotAllowedException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
