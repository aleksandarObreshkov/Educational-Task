package com.example.backend.controllers;

import com.example.services.CharacterService;
import com.example.constants.HttpStatus;
import com.example.controllers.CharacterController;
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
    public CharacterService service;

    public CharacterController controller;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        controller = new CharacterController(service);
    }

    @Test
    public void validFindAllRequest(){
        Character character = Mockito.mock(Character.class);
        ArrayList<Character> resultList = new ArrayList<>();
        resultList.add(character);
        when(service.findAll()).thenReturn(resultList);
        assertEquals(controller.get().getStatus(), HttpStatus.OK);
        assertEquals(controller.get().getBody(), resultList);
    }

    @Test
    public void validFindByIdRequest(){
        Character character = Mockito.mock(Character.class);
        when(service.findById(10L)).thenReturn(character);
        assertEquals(controller.get(10L).getBody(), character);
    }

    @Test
    public void findByIdNotFoundRequest(){
        when(service.findById(10L)).thenReturn(null);
        assertEquals(controller.get(10L).getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void validDeleteByIdRequest(){
        when(service.deleteById(10L)).thenReturn(true);
        assertEquals(controller.delete(10L).getStatus(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void deleteByIdNotFoundRequest(){
        when(service.deleteById(10L)).thenReturn(false);
        assertEquals(controller.get(10L).getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void validPostRequest(){
        Character character = Mockito.mock(Character.class);
        assertEquals(controller.post(character).getStatus(), HttpStatus.CREATED);
    }
}
