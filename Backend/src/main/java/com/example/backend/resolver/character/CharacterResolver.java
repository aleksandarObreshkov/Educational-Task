package com.example.backend.resolver.character;

import graphql.kickstart.tools.GraphQLResolver;
import model.Character;
import org.springframework.stereotype.Component;
import repositories.EntityRepository;

@Component
public class CharacterResolver implements GraphQLResolver<Character> {

    private final EntityRepository repository;

    public CharacterResolver(EntityRepository repository) {
        this.repository = repository;
    }

    public Iterable<Character> allCharacters() {
        return repository.findAll(Character.class);
    }

}
