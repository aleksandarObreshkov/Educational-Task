package com.example.backend.resolver.character;

import com.example.backend.errors.NotFoundException;
import model.Character;
import org.springframework.stereotype.Component;
import repositories.CharacterRepository;

import java.util.Optional;

@Component
public abstract class CharacterResolver {

    private final CharacterRepository repository;

    protected CharacterResolver(CharacterRepository repository) {
        this.repository = repository;
    }

    public Iterable<Character> allCharacters(){
        return repository.allCharacters();
    }

    protected <T> Iterable<T> allCharactersOfType(Class<T> type){
        return repository.findAll(type);
    }

    protected <T> Optional<T> characterWithIdAndType(Long id, Class<T> type){
        T characterToReturn = repository.findById(id, type);
        if (characterToReturn==null){
            throw new NotFoundException("No Droid with the specified id.");
        }
        return Optional.of(characterToReturn);
    }


}
