package com.example.backend.resolver.character;

import com.example.backend.errors.NotFoundException;
import graphql.GraphQLException;
import graphql.kickstart.tools.GraphQLResolver;
import model.Character;
import model.Human;
import org.springframework.stereotype.Component;
import repositories.EntityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class HumanResolver implements GraphQLResolver<Human> {

    private final EntityRepository repository;

    public HumanResolver(EntityRepository repository) {
        this.repository = repository;
    }

    public List<Human> allHumans() {

        List<Character> humans = repository.findAll(Character.class)
                .stream()
                .filter(character -> character.getCharacterType().equals("human"))
                .collect(Collectors.toList());

        List<Human> result = new ArrayList<>();

        for (Character human : humans) {
            result.add((Human) human);
        }
        return result;
    }

    public Optional<Human> human(Long id) {
        Optional<Human> humanToReturn = allHumans()
                .stream()
                .filter(human -> human.getId().equals(id))
                .findFirst();
        if (humanToReturn.isEmpty()){
            throw new NotFoundException("No Human with the specified id.");
        }
        return humanToReturn;
    }
}
