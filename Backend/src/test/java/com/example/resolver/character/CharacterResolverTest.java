package com.example.resolver.character;

import com.example.model.Character;
import com.example.resolver.EntityResolver;
import com.example.resolver.EntityResolverTest;
import com.example.repositories.spring.CharacterRepository;
import org.mockito.Mock;

public class CharacterResolverTest extends EntityResolverTest<Character, CharacterRepository> {

    @Mock
    CharacterRepository repository;

    @Mock
    Character character;

    @Override
    protected EntityResolver<Character, CharacterRepository> initResolver(CharacterRepository repository) {
        return new CharacterResolver(repository);
    }

    @Override
    protected CharacterRepository mockRepo() {
        return repository ;
    }

    @Override
    protected Character mockEntity() {
        return character;
    }
}
