package com.example.backend.controllers;

import com.example.constants.HttpStatus;
import com.example.controllers.StarshipController;
import com.example.repositories.EntityRepository;
import com.example.model.Starship;
import com.example.repositories.StarshipRepository;
import com.example.services.StarshipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class StarshipControllerTest {

    @Mock
    public StarshipRepository repository;

    @Mock
    public StarshipService service;

    public StarshipController controller;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        controller = new StarshipController(repository, service);
    }

    @Test
    public void validFindAllRequest(){
        Starship starship = Mockito.mock(Starship.class);
        ArrayList<Starship> resultList = new ArrayList<>();
        resultList.add(starship);
        when(repository.findAll()).thenReturn(resultList);
        assertEquals(controller.get().getStatus(), HttpStatus.OK);
        assertEquals(controller.get().getBody(), resultList);
    }

    @Test
    public void validFindByIdRequest(){
        Starship starship = Mockito.mock(Starship.class);
        when(repository.findById(10L)).thenReturn(starship);
        assertEquals(controller.get(10L).getBody(), starship);
    }

    @Test
    public void findByIdNotFoundRequest(){
        when(repository.findById(10L)).thenReturn(null);
        assertEquals(controller.get(10L).getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void validDeleteByIdRequest(){
        when(service.deleteById(10L)).thenReturn(true);
        assertEquals(controller.delete(10L).getStatus(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void deleteByIdNotFoundRequest(){
        when(repository.deleteById(10L)).thenReturn(false);
        assertEquals(controller.get(10L).getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void validPostRequest(){
        Starship starship = Mockito.mock(Starship.class);
        assertEquals(controller.post(starship).getStatus(), HttpStatus.CREATED);
    }
}
