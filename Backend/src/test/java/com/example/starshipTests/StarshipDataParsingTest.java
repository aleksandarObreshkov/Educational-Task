package com.example.starshipTests;

import com.example.controllers.StarshipController;
import com.example.errors.ExceptionResolver;
import com.example.spring_data_repositories.StarshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StarshipController.class)
public class StarshipDataParsingTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StarshipRepository repository;

    @BeforeEach
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.standaloneSetup(new StarshipController(repository))
                .setControllerAdvice(new ExceptionResolver())
                .build();
    }

    @Test
    public void addStarshipLengthExceptionsTest() throws Exception {

        String lengthAsChar = "{ \"name\":\"Rampage\", \"lengthInMeters\":\"w\"}";
        mockMvc.perform(post("/starships").content(lengthAsChar).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(500))
                .andExpect(content().string(containsString("not a valid Float value")));

        String negativeLength = "{ \"name\":\"Rampage\", \"lengthInMeters\":\"-1\"}";
        mockMvc.perform(post("/starships").content(negativeLength).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(422))
                .andExpect(content().string(containsString("greater than 0")));
    }

    @Test
    public void addStarshipNoNameExceptionTest() throws Exception {
        String noName = "{ \"lengthInMeters\":\"90\"}";
        mockMvc.perform(post("/starships")
                .content(noName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(422));
    }

    @Test
    public void getStarshipByIdExceptionTest() throws Exception {
        when(repository.findById(90L)).thenReturn(null);
        mockMvc.perform(get("/starships/90"))
                .andExpect(status().is(404));
    }

    @Test
    public void deleteStarshipByIdExceptionTest() throws Exception {
        when(repository.deleteStarshipById(90L)).thenReturn(null);
        mockMvc.perform(delete("/starships/90"))
                .andExpect(status().is(404));
    }
}
