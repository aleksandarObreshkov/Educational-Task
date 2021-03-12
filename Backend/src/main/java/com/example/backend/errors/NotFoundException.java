package com.example.backend.errors;


import graphql.GraphQLException;

public class NotFoundException extends GraphQLException {
    private final String message;

    public NotFoundException(String message) {
        super();
        this.message=message;

    }

    public String getMessage(){
        return message;
    }

}
