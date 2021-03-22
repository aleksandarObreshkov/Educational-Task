package com.example.resolver.character;

import com.example.errors.NotFoundException;
import graphql.kickstart.tools.GraphQLResolver;
import com.example.model.Human;
import org.springframework.stereotype.Component;
import com.example.repositories.CharacterRepository;
import java.util.Optional;

@Component
public class HumanResolver extends CharacterResolver implements GraphQLResolver<Human> {

    public HumanResolver(CharacterRepository repository) {
        super(repository);
    }

    public Iterable<Human> allHumans() {
        return null;//allCharactersOfType(Human.class);
    }

    public Optional<Human> human(Long id) {
        Human humanToReturn = null;//characterWithIdAndType(id, Human.class);
        if (humanToReturn == null) {
            throw new NotFoundException("No Human with the specified id.");
        }
        return Optional.of(humanToReturn);
    }

}
