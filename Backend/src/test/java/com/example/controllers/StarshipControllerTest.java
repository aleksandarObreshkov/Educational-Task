package com.example.controllers;

import com.example.errors.ExceptionResolver;
import com.example.model.Starship;
import com.example.services.StarshipService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(StarshipController.class)
public class StarshipControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private StarshipService service;

    private static Starship starship;

    @BeforeEach
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.standaloneSetup(new StarshipController(service))
                .setControllerAdvice(new ExceptionResolver())
                .build();
        instantiateStarship();
    }

    private void instantiateStarship(){
        starship = new Starship();
        starship.setName("Big Ship");
        starship.setLengthInMeters(2.2f);
    }

    @Test
    public void validGetAllStarships() throws Exception {
        List<Starship> starships = new ArrayList<>();
        starships.add(starship);
        when(service.findAll()).thenReturn(starships);
        mockMvc.perform(get("/starships"))
                .andExpect(status().is(200))
                .andExpect(content().string(containsString(starship.getName())));
    }

    @Test
    public void validGetStarshipById() throws Exception {
        when(service.findById(1L)).thenReturn(Optional.of(starship));
        mockMvc.perform(get("/starships/1"))
                .andExpect(status().is(200))
                .andExpect(content().string(containsString(starship.getName())));
    }

    @Test
    public void validAddStarship() throws Exception {
        when(service.save(starship)).thenReturn(starship);
        mockMvc.perform(post("/starships").content(new ObjectMapper().writeValueAsString(starship)).contentType("application/json"))
                .andExpect(status().is(201));
    }

    @Test
    public void validDeleteStarship() throws Exception {
        when(service.deleteById(1L)).thenReturn(true);
        mockMvc.perform(delete("/starships/1"))
                .andExpect(status().is(204));
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
        when(service.findById(90L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/starships/90"))
                .andExpect(status().is(404));
    }

    @Test
    public void deleteStarshipByIdExceptionTest() throws Exception {
        when(service.deleteById(90L)).thenReturn(false);
        mockMvc.perform(delete("/starships/90"))
                .andExpect(status().is(404));
    }
}
