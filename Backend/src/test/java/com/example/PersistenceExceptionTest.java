package com.example;

import com.example.controllers.MovieController;
import com.example.errors.ExceptionResolver;
import com.example.spring_data_repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import javax.persistence.PersistenceException;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
public class PersistenceExceptionTest {

    @MockBean
    public MovieRepository repository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setupMockMvc(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new MovieController(repository))
                .setControllerAdvice(new ExceptionResolver())
                .build();
    }

    @Test
    public void persistenceExceptionHandling() throws Exception {
        when(repository.deleteMovieById(10L)).thenThrow(new PersistenceException("Database error."));

        mockMvc.perform(delete("/movies/10"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Database error")));
    }
}
