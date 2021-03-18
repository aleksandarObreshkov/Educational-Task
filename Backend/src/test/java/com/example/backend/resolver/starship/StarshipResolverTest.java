package com.example.backend.resolver.starship;

import model.Starship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import repositories.EntityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class StarshipResolverTest {

    @Mock
    @Qualifier("entityRepository")
    private EntityRepository repository;

    private StarshipResolver resolver;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        resolver = new StarshipResolver(repository);
    }

    @Test
    public void allStarships(){
        List<Starship> starships = new ArrayList<>();
        when(repository.findAll(Starship.class)).thenReturn(starships);
        assertEquals(resolver.allStarships(), starships);
    }

    @Test
    public void starship(){
        Starship starship = Mockito.mock(Starship.class);
        when(repository.findById(10L, Starship.class)).thenReturn(starship);
        assertEquals(resolver.starship(10L), Optional.of(starship));
    }
}
