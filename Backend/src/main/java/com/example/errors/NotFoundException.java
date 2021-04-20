package com.example.errors;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class NotFoundException extends RuntimeException implements GraphQLError {
    
    private static final Long serialVersionUID = 1L;
    private final String message;

    public NotFoundException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return null;
    }
}
