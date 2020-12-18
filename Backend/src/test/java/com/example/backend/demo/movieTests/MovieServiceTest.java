package com.example.backend.demo.movieTests;
import com.example.backend.demo.controllers.MovieController;
import model.Movie;
import com.example.backend.demo.services.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.when;


@WebMvcTest(MovieController.class)
public class MovieServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    public MovieService service;


    private ObjectMapper mapper;

    @BeforeEach
    public void setupObjectMapper(){
        mapper=new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    private final Movie movie = new Movie("The Force Awakens",
            3.12f,
            LocalDate.parse("2020-12-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")));


    @Test
    public void getMovieByIdTest() throws Exception {
        when(service.getMovieById("1")).thenReturn(movie);
        mvc.perform(get("/movies/1")).andExpect(content().json(mapper.writeValueAsString(movie)));
    }

    @Test
    public void getAllMoviesTest() throws Exception {
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        when(service.getAll()).thenReturn(movies);
        mvc.perform(get("/movies")).andExpect(content().json(mapper.writeValueAsString(movies)));
    }

    @Test
    public void addMovieTest() throws Exception {
        doAnswer((i) -> {
            assertEquals(i.getArgument(0), movie);
            return null;
        }).when(service).addMovie(movie);
        service.addMovie(movie);
    }

    /*
    @Test
    public void deleteMovieTest() throws Exception {
        doAnswer((i) -> {
            assertEquals(i.getArgument(0), movie.getId());
            return null;
        }).when(service).deleteMovieById(movie.getId()+"");
        service.deleteMovieById(movie.getId()+"");
    }

     */

}
