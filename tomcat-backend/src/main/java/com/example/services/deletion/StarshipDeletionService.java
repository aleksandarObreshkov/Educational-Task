package com.example.services.deletion;

import com.example.model.Character;
import com.example.model.Human;
import com.example.model.Starship;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StarshipDeletionService extends DeletionService<Starship> {
    public StarshipDeletionService() {
        super();
    }

    @Override
    public void unlink(Starship entity) {
        List<Character> allCharacters = repository.findAll();
        for (Character character : allCharacters){
            if (character.getCharacterType().equals("human")){
                ((Human)character).getStarships().remove(entity);
            }
            repository.save(character);
        }
    }
}
