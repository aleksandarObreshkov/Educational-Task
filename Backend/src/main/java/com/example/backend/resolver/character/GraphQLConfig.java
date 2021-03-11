package com.example.backend.resolver.character;

import graphql.kickstart.tools.SchemaParserDictionary;
import model.Droid;
import model.Human;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQLConfig {

    @Bean
    public SchemaParserDictionary schemaParserDictionary(){
        return new SchemaParserDictionary()
                .add(Human.class)
                .add(Droid.class);
    }
}
