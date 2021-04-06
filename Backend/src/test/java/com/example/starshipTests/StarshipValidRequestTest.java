package com.example.starshipTests;

import com.example.spring_data_repositories.StarshipRepository;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.model.Starship;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StarshipValidRequestTest {

    @MockBean
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
        when(repository.findById(1L)).thenReturn(Optional.of(starship));
        assertEquals(Optional.of(starship), repository.findById(1L));
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
        when(repository.deleteStarshipById(1L)).thenReturn(1);
        assertEquals(repository.deleteStarshipById(1L), 1);
    }


}
