package com.example.backend.demo.movieTests;

import com.example.backend.demo.repositories.EntityRepository;
import model.Movie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MovieValidRequestTest {

    @MockBean
    private EntityRepository repository;

    private static final Movie movie = new Movie();

    @BeforeAll
    static void setUpMovie(){
        movie.setId(1L);
        movie.setTitle("Title");
        movie.setRating(2.2f);
    }

    @Test
    public void getAllMovies(){
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        when(repository.findAll(Movie.class)).thenReturn(movies);
        assertEquals(movies, repository.findAll(Movie.class));
    }

    @Test
    public void getMovieById(){
        when(repository.findById(1L,Movie.class)).thenReturn(movie);
        assertEquals(movie, repository.findById(1L,Movie.class));
    }

    @Test
    public void addMovie(){
        doAnswer((i) -> {
            assertEquals(i.getArgument(0), movie);
            return null;
        }).when(repository).save(movie);
        repository.save(movie);
    }

    @Test
    public void deleteMovie(){
        when(repository.deleteById(1L,Movie.class)).thenReturn(true);
        assertTrue(repository.deleteById(1L, Movie.class));
    }


}
