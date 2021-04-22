package com.example.config;

import graphql.ExecutionResult;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.ExecutionContext;
import graphql.execution.ExecutionStrategyParameters;
import graphql.execution.NonNullableFieldWasNullException;
import kotlin.jvm.Throws;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncTransactionalExecutionStrategy extends AsyncExecutionStrategy {

    @Transactional
    @Throws(exceptionClasses = NonNullableFieldWasNullException.class)
    public CompletableFuture<ExecutionResult> execute(ExecutionContext executionContext, ExecutionStrategyParameters parameters) {
        return super.execute(executionContext, parameters);
    }
}
