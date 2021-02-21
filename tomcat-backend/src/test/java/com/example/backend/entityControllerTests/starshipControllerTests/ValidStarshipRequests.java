package com.example.backend.entityControllerTests.starshipControllerTests;

import com.example.backend.constants.HttpStatus;
import com.example.backend.controllers.CharacterController;
import com.example.backend.controllers.StarshipController;
import com.example.backend.repositories.EntityRepository;
import model.Character;
import model.Starship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ValidStarshipRequests {

    @Mock
    public EntityRepository repository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void validFindAllRequest(){
        StarshipController controller = new StarshipController(repository);
        ArrayList<Starship> resultList = new ArrayList<>();
        when(repository.findAll(Starship.class)).thenReturn(resultList);
        assertEquals(controller.get().getStatus(), HttpStatus.OK);
    }

    @Test
    public void validFindByIdRequest(){
        StarshipController controller = new StarshipController(repository);
        Starship starship = Mockito.mock(Starship.class);
        when(repository.findById(10L, Starship.class)).thenReturn(starship);
        assertEquals(controller.get(10L).getBody(), starship);
    }

    @Test
    public void findByIdNotFoundRequest(){
        StarshipController controller = new StarshipController(repository);
        when(repository.findById(10L, Starship.class)).thenReturn(null);
        assertEquals(controller.get(10L).getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void validDeleteByIdRequest(){
        StarshipController controller = new StarshipController(repository);
        when(repository.deleteById(10L, Starship.class)).thenReturn(true);
        assertEquals(controller.delete(10L).getStatus(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void deleteByIdNotFoundRequest(){
        StarshipController controller = new StarshipController(repository);
        when(repository.deleteById(10L, Starship.class)).thenReturn(false);
        assertEquals(controller.get(10L).getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void validPostRequest(){
        StarshipController controller = new StarshipController(repository);
        Starship starship = Mockito.mock(Starship.class);
        assertEquals(controller.post(starship).getStatus(), HttpStatus.CREATED);
    }
}
