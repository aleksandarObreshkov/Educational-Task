package com.example.resolver.character;

import graphql.kickstart.tools.SchemaParserDictionary;
import com.example.model.Droid;
import com.example.model.Human;
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