package com.example.movieTests;

import com.example.spring_data_repositories.MovieRepository;
import com.example.model.Movie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MovieValidRequestTest {

    @MockBean
    private MovieRepository repository;

    private static final Movie movie = new Movie();

    @BeforeAll
    static void setUpMovie(){
        movie.setId(1L);
        movie.setTitle("Title");
        movie.setRating(2.2f);
        movie.setReleaseDate("2020-12-12");
    }

    @Test
    public void getAllMovies(){
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        when(repository.findAll()).thenReturn(movies);
        assertEquals(movies, repository.findAll());
    }

    @Test
    public void getMovieById(){
        when(repository.findById(1L)).thenReturn(Optional.of(movie));
        assertEquals(Optional.of(movie), repository.findById(1L));
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
        when(repository.deleteMovieById(1L)).thenReturn(1);
        assertEquals(repository.deleteMovieById(1L), 1);
    }


}
