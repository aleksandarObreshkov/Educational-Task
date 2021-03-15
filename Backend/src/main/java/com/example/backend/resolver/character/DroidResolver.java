package com.example.backend.resolver.character;

import graphql.kickstart.tools.GraphQLResolver;
import model.Droid;
import org.springframework.stereotype.Component;
import repositories.CharacterRepository;
import java.util.Optional;


@Component
public class DroidResolver extends CharacterResolver implements GraphQLResolver<Droid> {

    protected DroidResolver(CharacterRepository repository) {
        super(repository);
    }

    public Iterable<Droid> allDroids(){
        return allCharactersOfType(Droid.class);
    }

    public Optional<Droid> droid(Long id){
        return characterWithIdAndType(id, Droid.class);
    }

}
