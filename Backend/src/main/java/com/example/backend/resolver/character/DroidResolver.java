package com.example.backend.resolver.character;

import com.example.backend.errors.NotFoundException;
import graphql.kickstart.tools.GraphQLResolver;
import model.Droid;
import org.springframework.stereotype.Component;
import repositories.CharacterRepository;
import java.util.Optional;

@Component
public class DroidResolver extends CharacterResolver implements GraphQLResolver<Droid> {

    public DroidResolver(CharacterRepository repository) {
        super(repository);
    }

    public Iterable<Droid> allDroids(){
        return allCharactersOfType(Droid.class);
    }

    public Optional<Droid> droid(Long id){
        Droid droidToReturn =  characterWithIdAndType(id, Droid.class);
        if (droidToReturn==null){
            throw new NotFoundException("No Droid with the specified id.");
        }
        return Optional.of(droidToReturn);
    }

}
