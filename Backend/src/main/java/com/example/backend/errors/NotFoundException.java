package com.example.backend.errors;

import graphql.GraphQLException;

// TODO You've implemented Serializable (comes from the Throwable interface), but you haven't added a serialVersionUid
// constant to the class.
public class NotFoundException extends GraphQLException {

    private final String message; // TODO Why introduce a new message field when Throwable already has one.

    public NotFoundException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
