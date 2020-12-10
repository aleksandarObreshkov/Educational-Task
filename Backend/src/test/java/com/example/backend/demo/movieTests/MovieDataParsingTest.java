package com.example.backend.demo.movieTests;

import com.example.backend.demo.controllers.MovieController;
import com.example.backend.demo.errors.ExceptionResolver;
import com.example.backend.demo.services.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.io.IOException;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
public class MovieDataParsingTest {

    private MockMvc mockMvc;

    @MockBean
    private MovieService service;

    @BeforeEach
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.standaloneSetup(new MovieController(service))
                .setControllerAdvice(new ExceptionResolver())
                .build();
    }

    @Test
    public void addMovieInvalidRatingExceptionTest() throws Exception {

        String incorrectRatingMovie = "{\"title\" : \"A New Hope\",\n" +
                "  \"releaseDate\" : \"2018-03-25\",\n" +
                "  \"rating\" : w\n" +
                "}";


        mockMvc.perform(post("/movies").content(incorrectRatingMovie).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(415))
                .andExpect(content().string(containsString("Unrecognized token 'w'")));
    }

    @Test
    public void addMovieInvalidDateExceptionTest() throws Exception {

        String incorrectDateMovie = "{\"title\" : \"A New Hope\",\n" +
                "  \"releaseDate\" : \"2018/03-25\",\n" +
                "  \"rating\" : 3.2\n" +
                "}";
        mockMvc = MockMvcBuilders.standaloneSetup(new MovieController(service))
                .setControllerAdvice(new ExceptionResolver())
                .build();


        mockMvc.perform(post("/movies").content(incorrectDateMovie).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(415))
                .andExpect(content().string(containsString("Text '2018/03-25' could not be parsed")));
    }

    @Test
    public void deleteMovieByIdExceptionTest() throws Exception {

        doThrow(new IOException("No such id")).when(service).deleteMovieById("10");

        mockMvc.perform(delete("/movies/10"))
                .andExpect(status().is(415))
                .andExpect(content().string(containsString("No such id")));
    }

    @Test
    public void getMovieByIdExceptionTest() throws Exception {

        doThrow(new IOException("No such id")).when(service).getMovieById("10");

        mockMvc.perform(get("/movies/10"))
                .andExpect(status().is(415))
                .andExpect(content().string(containsString("No such id")));
    }


}