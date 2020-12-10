package com.example.backend.demo.starshipTests;

import com.example.backend.demo.controllers.StarshipController;
import com.example.backend.demo.services.StarshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.example.backend.demo.errors.ExceptionResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.io.IOException;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StarshipController.class)
public class StarshipDataParsingTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StarshipService service;

    @BeforeEach
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.standaloneSetup(new StarshipController(service))
                .setControllerAdvice(new ExceptionResolver())
                .build();
    }

    @Test
    public void addStarshipLengthExceptionsTest() throws Exception {

        String lengthAsChar = "{ \"name\":\"Rampage\", \"length\":\"w\"}";
        mockMvc.perform(post("/starships").content(lengthAsChar).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(415))
                .andExpect(content().string(containsString("not a valid Float value")));

        String negativeLength = "{ \"name\":\"Rampage\", \"length\":\"-1\"}";
        mockMvc.perform(post("/starships").content(negativeLength).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(415))
                .andExpect(content().string(containsString("greater than 0")));
    }

    @Test
    public void addStarshipNoNameExceptionTest() throws Exception {

        String noName = "{ \"length\":\"90\"}";
        mockMvc.perform(post("/starships")
                .content(noName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(415))
                .andExpect(content().string(containsString("Please provide a name")));
    }

    @Test
    public void getStarshipByIdExceptionTest() throws Exception {
        doThrow(new IOException("No such id")).when(service).getStarshipById("90");
        mockMvc.perform(get("/starships/90"))
                .andExpect(status().is(415))
                .andExpect(content().string(containsString("No such id")));
    }

    @Test
    public void deleteStarshipByIdExceptionTest() throws Exception {
        doThrow(new IOException("No such id")).when(service).deleteStarshipById("90");
        mockMvc.perform(delete("/starships/90"))
                .andExpect(status().is(415))
                .andExpect(content().string(containsString("No such id")));
    }



}
