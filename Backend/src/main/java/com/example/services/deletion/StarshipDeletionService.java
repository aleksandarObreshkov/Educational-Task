package com.example.services.deletion;

import com.example.model.Character;
import com.example.model.Human;
import com.example.model.Starship;
import com.example.repositories.spring.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StarshipDeletionService extends DeletionService<Starship> {
    public StarshipDeletionService(CharacterRepository repository) {
        super(repository);
    }

    @Override
    public void unlink(Starship entity) {
        List<Character> allCharacters = repository.findAll();
        for (Character character : allCharacters){
            if (character.getClass().equals(Human.class)){
                ((Human)character).getStarships().remove(entity);
            }
        }
        repository.saveAll(allCharacters);
    }
}
