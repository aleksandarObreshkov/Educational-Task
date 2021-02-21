package com.example.backend.entityControllerTests.characterControllerTests;

import com.example.backend.constants.HttpStatus;
import com.example.backend.controllers.CharacterController;
import com.example.backend.repositories.EntityRepository;
import model.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidCharacterRequests {
    //TODO: add the other possibilities, NotFound, empty array, etc.
    @Mock
    public EntityRepository repository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void validFindAllRequest(){
        CharacterController controller = new CharacterController(repository);
        ArrayList<Character> resultList = new ArrayList<>();
        when(repository.findAll(Character.class)).thenReturn(resultList);
        assertEquals(controller.get().getStatus(), HttpStatus.OK);
    }

    @Test
    public void validFindByIdRequest(){
        CharacterController controller = new CharacterController(repository);
        Character character = Mockito.mock(Character.class);
        when(repository.findById(10L, Character.class)).thenReturn(character);
        assertEquals(controller.get(10L).getBody(), character);
    }

    @Test
    public void findByIdNotFoundRequest(){
        CharacterController controller = new CharacterController(repository);
        when(repository.findById(10L, Character.class)).thenReturn(null);
        assertEquals(controller.get(10L).getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void validDeleteByIdRequest(){
        CharacterController controller = new CharacterController(repository);
        when(repository.deleteById(10L, Character.class)).thenReturn(true);
        assertEquals(controller.delete(10L).getStatus(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void deleteByIdNotFoundRequest(){
        CharacterController controller = new CharacterController(repository);
        when(repository.deleteById(10L, Character.class)).thenReturn(false);
        assertEquals(controller.get(10L).getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void validPostRequest(){
        CharacterController controller = new CharacterController(repository);
        Character character = Mockito.mock(Character.class);
        assertEquals(controller.post(character).getStatus(), HttpStatus.CREATED);
    }
}
