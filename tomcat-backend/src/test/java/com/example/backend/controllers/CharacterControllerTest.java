package com.example.backend.controllers;

import com.example.constants.HttpStatus;
import com.example.controllers.CharacterController;
import com.example.repositories.CharacterRepository;
import com.example.repositories.EntityRepository;
import com.example.model.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CharacterControllerTest {

    @Mock
    public CharacterRepository<Character> repository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void validFindAllRequest(){
        CharacterController controller = new CharacterController(repository);
        Character character = Mockito.mock(Character.class);
        ArrayList<Character> resultList = new ArrayList<>();
        resultList.add(character);
        when(repository.findAll()).thenReturn(resultList);
        assertEquals(controller.get().getStatus(), HttpStatus.OK);
        assertEquals(controller.get().getBody(), resultList);
    }

    @Test
    public void validFindByIdRequest(){
        CharacterController controller = new CharacterController(repository);
        Character character = Mockito.mock(Character.class);
        when(repository.findById(10L)).thenReturn(character);
        assertEquals(controller.get(10L).getBody(), character);
    }

    @Test
    public void findByIdNotFoundRequest(){
        CharacterController controller = new CharacterController(repository);
        when(repository.findById(10L)).thenReturn(null);
        assertEquals(controller.get(10L).getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void validDeleteByIdRequest(){
        CharacterController controller = new CharacterController(repository);
        when(repository.deleteById(10L)).thenReturn(true);
        assertEquals(controller.delete(10L).getStatus(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void deleteByIdNotFoundRequest(){
        CharacterController controller = new CharacterController(repository);
        when(repository.deleteById(10L)).thenReturn(false);
        assertEquals(controller.get(10L).getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void validPostRequest(){
        CharacterController controller = new CharacterController(repository);
        Character character = Mockito.mock(Character.class);
        assertEquals(controller.post(character).getStatus(), HttpStatus.CREATED);
    }
}
