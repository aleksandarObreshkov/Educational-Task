package com.example.resolver.character;

import com.example.model.Character;
import com.example.repositories.CharacterRepository;

abstract class CharacterResolver {

    private final CharacterRepository<? extends Character> repository;

    protected CharacterResolver(CharacterRepository<? extends Character> repository) {
        this.repository = repository;
    }

    public Iterable<Character> allCharacters() {
        return repository.findAllCharacters();
    }

    public Iterable<? extends Character> allCharactersOfType() {
        return repository.findAll();
    }

    public <T extends Character> T characterWithIdAndType(Long id) {
        return repository.findById(id);
    }

}
