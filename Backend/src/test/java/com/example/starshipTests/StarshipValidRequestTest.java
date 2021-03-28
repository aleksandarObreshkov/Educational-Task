package com.example.starshipTests;

import com.example.repositories.StarshipRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.repositories.EntityRepository;
import com.example.model.Movie;
import com.example.model.Starship;
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
    @Qualifier("entityRepository")
    private StarshipRepository repository;

    private static final Starship starship = new Starship();

    @BeforeAll
    static void setUpStarship(){
        starship.setId(1L);
        starship.setName("Big Ship");
        starship.setLengthInMeters(2.2f);
    }

    @Test
    public void getAllStarships(){
        List<Starship> starships = new ArrayList<>();
        starships.add(starship);
        when(repository.findAll()).thenReturn(starships);
        assertEquals(starships, repository.findAll());
    }

    @Test
    public void getStarshipById(){
        when(repository.findById(1L)).thenReturn(starship);
        assertEquals(starship, repository.findById(1L));
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
        when(repository.deleteById(1L)).thenReturn(true);
        assertTrue(repository.deleteById(1L));
    }


}