package com.example.controllers;

import com.example.errors.ExceptionResolver;
import com.example.model.Movie;
import com.example.services.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private MovieService service;

    private static Movie movie;

    @BeforeEach
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.standaloneSetup(new MovieController(service))
                .setControllerAdvice(new ExceptionResolver())
                .build();
        instantiateMovie();
    }

    private void instantiateMovie(){
        movie = new Movie();
        movie.setTitle("Title");
        movie.setRating(1.1f);
        movie.setReleaseDate("1998-09-12");
    }

    @Test
    public void validGetAllMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        when(service.findAll()).thenReturn(movies);
        mockMvc.perform(get("/movies"))
                .andExpect(status().is(200))
                .andExpect(content().string(containsString(movie.getTitle())));
    }

    @Test
    public void validGetMovieById() throws Exception {
        when(service.findById(1L)).thenReturn(Optional.of(movie));
        mockMvc.perform(get("/movies/1"))
                .andExpect(status().is(200))
                .andExpect(content().string(containsString(movie.getTitle())));
    }

    @Test
    public void addMovie() throws Exception {
        when(service.save(movie)).thenReturn(movie);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mockMvc.perform(post("/movies").content(mapper.writeValueAsString(movie)).contentType("application/json"))
                .andExpect(status().is(201));
    }

    @Test
    public void validDeleteMovie() throws Exception {
        when(service.deleteById(1L)).thenReturn(true);
        mockMvc.perform(delete("/movies/1"))
                .andExpect(status().is(204));
    }


    @Test
    public void addMovieInvalidRatingExceptionTest() throws Exception {

        String incorrectRatingMovie = "{\"title\" : \"A New Hope\",\n" +
                "  \"releaseDate\" : \"2018-03-25\",\n" +
                "  \"rating\" : \"w\"\n" +
                "}";

        mockMvc.perform(post("/movies").content(incorrectRatingMovie).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(500))
                .andExpect(content().string(containsString("Cannot deserialize value of type `java.lang.Float`")));
    }

    @Test
    public void addMovieInvalidDateExceptionTest() throws Exception {

        String incorrectDateMovie = "{\"title\" : \"A New Hope\",\n" +
                "  \"releaseDate\" : \"2018/03-25\",\n" +
                "  \"rating\" : 3.2\n" +
                "}";

        mockMvc.perform(post("/movies").content(incorrectDateMovie).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(500))
                .andExpect(content().string(containsString("Text '2018/03-25' could not be parsed")));
    }

    @Test
    public void deleteMovieByIdExceptionTest() throws Exception {
        when(service.deleteById(10L)).thenReturn(false);
        mockMvc.perform(delete("/movies/10"))
                .andExpect(status().is(404));
    }

    @Test
    public void getMovieByIdExceptionTest() throws Exception {
        when(service.findById(10L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/movies/10"))
                .andExpect(status().is(404));
    }

}
