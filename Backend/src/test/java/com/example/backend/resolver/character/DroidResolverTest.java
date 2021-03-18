package com.example.backend.resolver.character;

import model.Droid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import repositories.CharacterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DroidResolverTest {

    @Mock
    @Qualifier("characterRepository")
    private CharacterRepository repository;

    private DroidResolver resolver;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        resolver = new DroidResolver(repository);
    }

    @Test
    public void allDroids(){
        List<Droid> droids = new ArrayList<>();
        when(repository.findAll(Droid.class)).thenReturn(droids);
        assertEquals(resolver.allDroids(), droids);
    }

    @Test
    public void human(){
        Droid droid = Mockito.mock(Droid.class);
        when(repository.findById(10L, Droid.class)).thenReturn(droid);
        assertEquals(resolver.droid(10L), Optional.of(droid));
    }
}
