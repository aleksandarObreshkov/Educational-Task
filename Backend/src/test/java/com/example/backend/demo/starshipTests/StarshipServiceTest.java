package com.example.backend.demo.starshipTests;

import com.example.backend.demo.controllers.StarshipController;
import model.Starship;
import com.example.backend.demo.services.StarshipService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(StarshipController.class)
public class StarshipServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StarshipService service;

    private ObjectMapper mapper;

    @BeforeEach
    public void setupObjectMapper(){
        mapper=new ObjectMapper();
    }

    private final Starship starship = new Starship("Dark Knight", 2.3f);

    @Test
    public void getAllStarshipsTest() throws Exception {
        List<Starship> starships = new ArrayList<>();
        starships.add(starship);
        when(service.getAll()).thenReturn(starships);
        mvc.perform(get("/starships")).andExpect(content().json(mapper.writeValueAsString(starships)));
    }

    @Test
    public void getCharacterByIdTest() throws Exception {
        when(service.getStarshipById("1")).thenReturn(starship);
        mvc.perform(get("/starships/1")).andExpect(content().json(mapper.writeValueAsString(starship)));
    }

    @Test
    public void addStarshipTest() throws Exception {
        doAnswer((i) -> {
            assertEquals(i.getArgument(0), starship);
            return null;
        }).when(service).addStarship(starship);
        service.addStarship(starship);
    }

    /*
    @Test
    public void deleteCharacterByIdTest() throws Exception {
        doAnswer((i) -> {
            assertEquals(i.getArgument(0), starship.getId());
            return null;
        }).when(service).deleteStarshipById(starship.getId()+"");
        service.deleteStarshipById(starship.getId()+"");
    }

     */
}
