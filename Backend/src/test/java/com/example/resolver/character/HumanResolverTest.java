package com.example.resolver.character;

import com.example.model.Human;
import com.example.resolver.EntityResolver;
import com.example.resolver.EntityResolverTest;
import com.example.repositories.CharacterRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class HumanResolverTest extends EntityResolverTest<Human, CharacterRepository<Human>> {

    @Mock
    CharacterRepository<Human> repository;

    @Mock
    Human human;

    @Override
    protected EntityResolver<Human, CharacterRepository<Human>> initResolver(CharacterRepository<Human> repository) {
        return new HumanResolver(repository);
    }

    @Override
    protected CharacterRepository<Human> mockRepo() {
        return repository;
    }

    @Override
    protected Human mockEntity() {
        return human;
    }

    @Override
    @Test
    public void findAllTest() {
        List<Human> entities = new ArrayList<>();
        entities.add(entity);
        when(repository.findAllOfType()).thenReturn(entities);
        assertEquals(resolver.all(), entities);
    }
}
