package com.example.resolver.character;

import com.example.model.Character;
import com.example.repositories.CharacterRepository;
import com.example.resolver.EntityResolver;

abstract class CharacterResolver<T extends Character> extends EntityResolver<T, CharacterRepository<T>> {

    protected CharacterResolver(CharacterRepository<T> repository){
        super(repository);
    }

    protected CharacterResolver(Class<T> type) {
        this(new CharacterRepository<>(type));
    }

    public Iterable<Character> allCharacters() {
        return repository.findAllCharacters();
    }

    @Override
    public Iterable<T> all() {
        return repository.findAllOfType();
    }
}
