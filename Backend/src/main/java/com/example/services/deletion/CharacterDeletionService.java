package com.example.services.deletion;

import com.example.model.Character;
import com.example.model.Human;
import com.example.repositories.spring.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterDeletionService extends DeletionService<Character> {

    public CharacterDeletionService(CharacterRepository repository) {
        super(repository);
    }

    @Override
    public void unlink(Character entity) {
        List<Character> allCharacters = repository.findAll();
        for (Character character : allCharacters){
            character.getFriends().remove(entity);
        }
        entity.setAppearsIn(null);
        entity.setFriends(null);
        if (entity.getClass().equals(Human.class)){
            ((Human)entity).setStarships(null);
        }
        repository.save(entity);
        repository.saveAll(allCharacters);
    }
}
