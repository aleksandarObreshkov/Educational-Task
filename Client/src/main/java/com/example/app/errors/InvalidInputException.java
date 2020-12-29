package com.example.app.errors;

public class InvalidInputException extends RuntimeException{

    public InvalidInputException(String message, Throwable cause) {
        super(message,cause);
    }
}
