package com.example.backend.demo.starshipTests;

import com.example.backend.demo.controllers.StarshipController;
import repositories.EntityRepository;
import model.Starship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.example.backend.demo.errors.ExceptionResolver;
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
    private EntityRepository repository;

    @BeforeEach
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.standaloneSetup(new StarshipController(repository))
                .setControllerAdvice(new ExceptionResolver())
                .build();
    }

    @Test
    public void addStarshipLengthExceptionsTest() throws Exception {

        String lengthAsChar = "{ \"name\":\"Rampage\", \"length\":\"w\"}";
        mockMvc.perform(post("/starships").content(lengthAsChar).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(content().string(containsString("not a valid Float value")));

        String negativeLength = "{ \"name\":\"Rampage\", \"length\":\"-1\"}";
        mockMvc.perform(post("/starships").content(negativeLength).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(content().string(containsString("greater than 0")));
    }

    @Test
    public void addStarshipNoNameExceptionTest() throws Exception {
        String noName = "{ \"length\":\"90\"}";
        mockMvc.perform(post("/starships")
                .content(noName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    public void getStarshipByIdExceptionTest() throws Exception {
        when(repository.findById(90L, Starship.class)).thenReturn(null);
        mockMvc.perform(get("/starships/90"))
                .andExpect(status().is(404));
    }

    @Test
    public void deleteStarshipByIdExceptionTest() throws Exception {
        when(repository.deleteById(90L, Starship.class)).thenReturn(false);
        mockMvc.perform(delete("/starships/90"))
                .andExpect(status().is(404));
    }
}
