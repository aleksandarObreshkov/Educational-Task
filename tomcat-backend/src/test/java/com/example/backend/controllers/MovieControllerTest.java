package com.example.backend.controllers;

import com.example.backend.constants.HttpStatus;
import com.example.repositories.EntityRepository;
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
    public EntityRepository repository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void validFindAllRequest(){
        MovieController controller = new MovieController(repository);
        Movie movie = Mockito.mock(Movie.class);
        ArrayList<Movie> resultList = new ArrayList<>();
        resultList.add(movie);
        when(repository.findAll(Movie.class)).thenReturn(resultList);
        assertEquals(controller.get().getStatus(), HttpStatus.OK);
        assertEquals(controller.get().getBody(), resultList);
    }

    @Test
    public void validFindByIdRequest(){
        MovieController controller = new MovieController(repository);
        Movie movie = Mockito.mock(Movie.class);
        when(repository.findById(10L, Movie.class)).thenReturn(movie);
        assertEquals(controller.get(10L).getBody(), movie);
    }

    @Test
    public void findByIdNotFoundRequest(){
        MovieController controller = new MovieController(repository);
        when(repository.findById(10L, Movie.class)).thenReturn(null);
        assertEquals(controller.get(10L).getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void validDeleteByIdRequest(){
        MovieController controller = new MovieController(repository);
        when(repository.deleteById(10L, Movie.class)).thenReturn(true);
        assertEquals(controller.delete(10L).getStatus(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void deleteByIdNotFoundRequest(){
        MovieController controller = new MovieController(repository);
        when(repository.deleteById(10L, Movie.class)).thenReturn(false);
        assertEquals(controller.get(10L).getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void validPostRequest(){
        MovieController controller = new MovieController(repository);
        Movie movie = Mockito.mock(Movie.class);
        assertEquals(controller.post(movie).getStatus(), HttpStatus.CREATED);
    }
}
