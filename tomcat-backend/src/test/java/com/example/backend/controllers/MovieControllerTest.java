package com.example.backend.controllers;

import com.example.services.MovieService;
import com.example.constants.HttpStatus;
import com.example.controllers.MovieController;
import com.example.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MovieControllerTest {

    @Mock
    public MovieService service;

    public MovieController controller;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        controller = new MovieController(service);
    }

    @Test
    public void validFindAllRequest(){
        Movie movie = Mockito.mock(Movie.class);
        ArrayList<Movie> resultList = new ArrayList<>();
        resultList.add(movie);
        when(service.findAll()).thenReturn(resultList);
        assertEquals(controller.get().getStatus(), HttpStatus.OK);
        assertEquals(controller.get().getBody(), resultList);
    }

    @Test
    public void validFindByIdRequest(){
        Movie movie = Mockito.mock(Movie.class);
        when(service.findById(10L)).thenReturn(movie);
        assertEquals(controller.get(10L).getBody(), movie);
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
        Movie movie = Mockito.mock(Movie.class);
        assertEquals(controller.post(movie).getStatus(), HttpStatus.CREATED);
    }
}
