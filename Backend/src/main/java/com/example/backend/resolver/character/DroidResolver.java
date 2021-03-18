package com.example.backend.resolver.character;

import com.example.backend.errors.NotFoundException;
import graphql.kickstart.tools.GraphQLResolver;
import model.Droid;
import org.springframework.stereotype.Component;
import repositories.CharacterRepository;
import java.util.Optional;

@Component
// TODO Currently, there's no benefit to implementing GraphQLResolver, as that interface is used when loading the
// value of a field is not a trivial operation - friends, starships (see
// https://www.baeldung.com/spring-graphql#5-field-resolvers-for-complex-values). What you're doing is calling the
// allDroids and droid methods directly in QueryResolver and that would work even without implementing anything here.
public class DroidResolver extends CharacterResolver implements GraphQLResolver<Droid> {

    // TODO The content of this class is mostly identical to HumanResolver. The only difference is the Class<?> value
    // you're passing to the protected methods. Think of a way to remove this duplication.
    public DroidResolver(CharacterRepository repository) {
        super(repository);
    }

    public Iterable<Droid> allDroids() {
        return allCharactersOfType(Droid.class);
    }

    public Optional<Droid> droid(Long id) {
        Droid droidToReturn = characterWithIdAndType(id, Droid.class);
        if (droidToReturn == null) {
            // TODO It's a good pattern to add the problematic input to the exception message.
            throw new NotFoundException("No Droid with the specified id.");
        }
        return Optional.of(droidToReturn);
    }

}
