package com.example.backend.resolver.character;

import org.springframework.stereotype.Component;

import model.Character;
import repositories.CharacterRepository;

@Component // TODO This is unnecessary, because Spring can't really create an instance of an abstract class.
public abstract class CharacterResolver {

    private final CharacterRepository repository;

    protected CharacterResolver(CharacterRepository repository) {
        this.repository = repository;
    }

    public Iterable<Character> allCharacters() {
        return repository.allCharacters();
    }

    protected <T> Iterable<T> allCharactersOfType(Class<T> type) {
        return repository.findAll(type);
    }

    protected <T> T characterWithIdAndType(Long id, Class<T> type) {
        return repository.findById(id, type);
    }

}
