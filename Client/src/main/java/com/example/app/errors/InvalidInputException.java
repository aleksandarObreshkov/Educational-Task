package com.example.app.errors;

// TODO Add a serialVersionUid constant.
public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }

}
