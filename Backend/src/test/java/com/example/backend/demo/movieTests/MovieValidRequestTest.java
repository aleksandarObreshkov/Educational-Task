package com.example.backend.demo.movieTests;

import com.example.backend.demo.services.EntityService;
import model.Movie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MovieValidRequestTest {

    @MockBean
    private EntityService service;

    private static final Movie movie = new Movie();

    @BeforeAll
    static void setupMovie(){
        movie.setId(1L);
        movie.setTitle("Title");
        movie.setRating(2.2f);
    }

    @Test
    public void getAllMovies(){
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        when(service.getAll(Movie.class)).thenReturn(ResponseEntity.ok(movies));
        assertEquals(ResponseEntity.ok(movies),service.getAll(Movie.class));
    }

    @Test
    public void getMovieById(){
        when(service.getById(1L,Movie.class)).thenReturn(ResponseEntity.ok(movie));
        assertEquals(ResponseEntity.ok(movie),service.getById(1L,Movie.class));
    }

    @Test
    public void addMovie(){
        when(service.add(movie)).thenReturn(ResponseEntity.status(HttpStatus.OK).build());
        assertEquals(ResponseEntity.status(HttpStatus.OK).build(),service.add(movie));
    }

    @Test
    public void deleteMovie(){
        when(service.deleteById(1L,Movie.class)).thenReturn(ResponseEntity.status(HttpStatus.OK).build());
        assertEquals(ResponseEntity.status(HttpStatus.OK).build(),service.deleteById(1L,Movie.class));
    }


}
