package com.example.resolver.movie;

import com.example.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import com.example.repositories.EntityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieResolverTest {

    @Mock
    @Qualifier("entityRepository")
    private EntityRepository repository;

    private MovieResolver resolver;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        resolver = new MovieResolver(repository);
    }

    @Test
    public void allMovies(){
        List<Movie> movies = new ArrayList<>();
        when(repository.findAll(Movie.class)).thenReturn(movies);
        assertEquals(resolver.allMovies(), movies);
    }

    @Test
    public void movie(){
        Movie movie = Mockito.mock(Movie.class);
        when(repository.findById(10L, Movie.class)).thenReturn(movie);
        assertEquals(resolver.movie(10L), Optional.of(movie));
    }


}
