package com.example.backend.controllers;

import com.example.constants.HttpStatus;
import com.example.controllers.MovieController;
import com.example.repositories.EntityRepository;
import com.example.model.Movie;
import com.example.repositories.MovieRepository;
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
    public MovieRepository repository;

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
        when(repository.findAll()).thenReturn(resultList);
        assertEquals(controller.get().getStatus(), HttpStatus.OK);
        assertEquals(controller.get().getBody(), resultList);
    }

    @Test
    public void validFindByIdRequest(){
        MovieController controller = new MovieController(repository);
        Movie movie = Mockito.mock(Movie.class);
        when(repository.findById(10L)).thenReturn(movie);
        assertEquals(controller.get(10L).getBody(), movie);
    }

    @Test
    public void findByIdNotFoundRequest(){
        MovieController controller = new MovieController(repository);
        when(repository.findById(10L)).thenReturn(null);
        assertEquals(controller.get(10L).getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void validDeleteByIdRequest(){
        MovieController controller = new MovieController(repository);
        when(repository.deleteById(10L)).thenReturn(true);
        assertEquals(controller.delete(10L).getStatus(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void deleteByIdNotFoundRequest(){
        MovieController controller = new MovieController(repository);
        when(repository.deleteById(10L)).thenReturn(false);
        assertEquals(controller.get(10L).getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void validPostRequest(){
        MovieController controller = new MovieController(repository);
        Movie movie = Mockito.mock(Movie.class);
        assertEquals(controller.post(movie).getStatus(), HttpStatus.CREATED);
    }
}
