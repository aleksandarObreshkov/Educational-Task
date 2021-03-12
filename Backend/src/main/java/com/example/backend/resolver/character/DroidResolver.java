package com.example.backend.resolver.character;

import com.example.backend.errors.NotFoundException;
import graphql.kickstart.tools.GraphQLResolver;
import model.Character;
import model.Droid;
import org.springframework.stereotype.Component;
import repositories.EntityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DroidResolver implements GraphQLResolver<Droid> {

    private final EntityRepository repository;

    public DroidResolver(EntityRepository repository) {
        this.repository = repository;
    }

    public List<Droid> allDroids(){

        List<Character> droids = repository.findAll(Character.class)
                .stream()
                .filter(character -> character.getCharacterType().equals("droid"))
                .collect(Collectors.toList());

        List<Droid> result = new ArrayList<>();

        for (Character droid: droids) {
            result.add((Droid)droid);
        }
        return result;
    }

    public Optional<Droid> droid(Long id){
        Optional<Droid> droidToReturn = allDroids()
                .stream()
                .filter(droid -> droid.getId().equals(id))
                .findFirst();
        if (droidToReturn.isEmpty()){
            throw new NotFoundException("No Droid with the specified id.");
        }
        return droidToReturn;
    }

}
