package com.example.backend.resolver.character;

import graphql.kickstart.tools.GraphQLResolver;
import model.Human;
import org.springframework.stereotype.Component;
import repositories.CharacterRepository;
import java.util.Optional;


@Component
public class HumanResolver extends CharacterResolver implements GraphQLResolver<Human> {

    public HumanResolver(CharacterRepository repository) {
        super(repository);
    }

    public Iterable<Human> allHumans() {
        return allCharactersOfType(Human.class);
    }

    public Optional<Human> human(Long id) {
        return characterWithIdAndType(id, Human.class);
    }

}
