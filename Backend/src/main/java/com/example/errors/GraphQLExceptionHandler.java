package com.example.errors;

import graphql.GraphQLException;
import graphql.kickstart.spring.error.ThrowableGraphQLError;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
public class GraphQLExceptionHandler {

    // TODO Try implementing GraphQLError (or extending graphql.kickstart.execution.error.GenericGraphQLError) in the
    // exceptions you want to show in your API. Then this class might not be necessary.
    @ExceptionHandler(GraphQLException.class)
    public ThrowableGraphQLError handleGraphQlException(GraphQLException e) {
        return new ThrowableGraphQLError(e);
    }

}
