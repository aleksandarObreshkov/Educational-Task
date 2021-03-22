package com.example.app.errors;

public class ServerException extends RuntimeException{

    private final String message;

    public ServerException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
