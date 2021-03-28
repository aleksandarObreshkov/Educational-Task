package com.example.app.errors;

public class InvalidInputException extends RuntimeException {

    private static final Long serialVersionUid = 1L;

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
