package com.example.resolver;

import com.example.errors.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public abstract class EntityResolverTest<T, R extends JpaRepository<T, Long>> {

    protected EntityResolver<T, R> resolver;

    protected R repository;
    protected T entity;

    protected abstract EntityResolver<T, R> initResolver(R repository);

    protected abstract R mockRepo();

    protected abstract T mockEntity();

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        entity = mockEntity();
        repository = mockRepo();
        resolver = initResolver(repository);
    }

    @Test
    public void notFoundExceptionTest(){
        when(repository.findById(10L)).thenReturn(Optional.empty());
        Throwable thrownException = assertThrows(NotFoundException.class, () -> resolver.entityWithId(10L));
        // TODO Use assertTrue instead of assert - JUnit assert* methods print better error messages.
        assert(thrownException.getMessage().contains("No entity with the specified id."));
    }

    @Test
    public void findAllTest(){
        List<T> entities = new ArrayList<>();
        entities.add(entity);
        when(repository.findAll()).thenReturn(entities);
        assertEquals(resolver.all(), entities);
    }

    @Test
    public void findByIdTest(){
        when(repository.findById(10L)).thenReturn(Optional.of(entity));
        assertEquals(resolver.entityWithId(10L), Optional.of(entity));
    }
}