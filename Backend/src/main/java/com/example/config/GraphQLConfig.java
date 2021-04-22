package com.example.config;

import graphql.execution.ExecutionStrategy;
import graphql.kickstart.tools.SchemaParserDictionary;
import com.example.model.Droid;
import com.example.model.Human;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class GraphQLConfig {
    @Bean
    public SchemaParserDictionary schemaParserDictionary(){
        return new SchemaParserDictionary()
                .add(Human.class)
                .add(Droid.class);
    }

    @Bean
    public Map<String, ExecutionStrategy> executionStrategies() {
        Map<String, ExecutionStrategy> executionStrategyMap = new HashMap<>();
        executionStrategyMap.put("queryExecutionStrategy", new AsyncTransactionalExecutionStrategy());
        return executionStrategyMap;
    }
}
