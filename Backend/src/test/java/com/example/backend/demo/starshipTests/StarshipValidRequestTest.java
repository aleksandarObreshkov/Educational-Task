package com.example.backend.demo.starshipTests;

import org.springframework.boot.test.context.SpringBootTest;
import repositories.EntityRepository;
import model.Movie;
import model.Starship;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StarshipValidRequestTest {

    @MockBean
    private EntityRepository repository;

    private static final Starship starship = new Starship();

    @BeforeAll
    static void setUpStarship(){
        starship.setId(1L);
        starship.setName("Big Ship");
        starship.setLength(2.2f);
    }

    @Test
    public void getAllStarships(){
        List<Starship> starships = new ArrayList<>();
        starships.add(starship);
        when(repository.findAll(Starship.class)).thenReturn(starships);
        assertEquals(starships, repository.findAll(Starship.class));
    }

    @Test
    public void getStarshipById(){
        when(repository.findById(1L,Starship.class)).thenReturn(starship);
        assertEquals(starship, repository.findById(1L,Starship.class));
    }

    @Test
    public void addStarship(){
        doAnswer((i) -> {
            assertEquals(i.getArgument(0), starship);
            return null;
        }).when(repository).save(starship);
        repository.save(starship);
    }

    @Test
    public void deleteStarship(){
        when(repository.deleteById(1L,Movie.class)).thenReturn(true);
        assertTrue(repository.deleteById(1L, Movie.class));
    }


}
