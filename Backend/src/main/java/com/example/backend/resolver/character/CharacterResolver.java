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

    protected <T> T characterWithIdAndType(Long id, Class<T> type){
        return repository.findById(id, type);
    }
}
