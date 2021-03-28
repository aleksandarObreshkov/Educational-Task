package com.example.resolver.character;

import com.example.model.Droid;
import com.example.resolver.EntityResolver;
import com.example.resolver.EntityResolverTest;
import com.example.repositories.CharacterRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DroidResolverTest extends EntityResolverTest<Droid, CharacterRepository<Droid>> {

    @Mock
    CharacterRepository<Droid> repository;

    @Mock
    Droid droid;

    @Override
    protected EntityResolver<Droid, CharacterRepository<Droid>> initResolver(CharacterRepository<Droid> repository) {
        return new DroidResolver(repository);
    }

    @Override
    protected CharacterRepository<Droid> mockRepo() {
        return repository;
    }

    @Override
    protected Droid mockEntity() {
        return droid;
    }

    @Override
    @Test
    public void findAllTest() {
        List<Droid> entities = new ArrayList<>();
        entities.add(entity);
        when(repository.findAllOfType()).thenReturn(entities);
        assertEquals(resolver.all(), entities);
    }
}
