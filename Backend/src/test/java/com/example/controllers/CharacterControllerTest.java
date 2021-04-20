package com.example.controllers;

import com.example.errors.ExceptionResolver;
import com.example.model.Character;
import com.example.model.Droid;
import com.example.model.Human;
import com.example.services.CharacterService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CharacterController.class)
public class CharacterControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CharacterService service;

    private static Human human;
    private static Droid droid;

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CharacterController(service))
                .setControllerAdvice(new ExceptionResolver())
                .build();
        instantiateHuman();
        instantiateDroid();
    }

    private void instantiateHuman(){
        human = new Human();
        human.setName("Human");
        human.setAge(12);
        human.setForceUser(true);
    }

    private void instantiateDroid(){
        droid = new Droid();
        droid.setName("Droid");
        droid.setAge(10);
        droid.setPrimaryFunction("primary function");
    }

    @Test
    public void validGetAllCharactersTest() throws Exception {
        List<Character> characters = new ArrayList<>();
        characters.add(human);
        characters.add(droid);
        when(service.findAll()).thenReturn(characters);
        mockMvc.perform(get("/characters"))
                .andExpect(status().is(200))
                .andExpect(content().string(containsString(droid.getName())))
                .andExpect(content().string(containsString(human.getName())));
    }

    @Test
    public void validGetHumanByIdTest() throws Exception {
        when(service.findById(10L)).thenReturn(Optional.of(human));
        mockMvc.perform(get("/characters/10"))
                .andExpect(status().is(200))
                .andExpect(content().string(containsString(human.getName())));
    }

    @Test
    public void validGetDroidByIdTest() throws Exception {
        when(service.findById(10L)).thenReturn(Optional.of(droid));
        mockMvc.perform(get("/characters/10"))
                .andExpect(status().is(200))
                .andExpect(content().string(containsString(droid.getName())));
    }

    @Test
    public void validAddHumanTest() throws Exception {
        when(service.save(human)).thenReturn(human);
        mockMvc.perform(post("/characters").content(new ObjectMapper().writeValueAsString(human)).contentType("application/json"))
                .andExpect(status().is(201));
    }

    @Test
    public void validAddDroidTest() throws Exception {
        when(service.save(droid)).thenReturn(droid);
        mockMvc.perform(post("/characters").content(new ObjectMapper().writeValueAsString(droid)).contentType("application/json"))
                .andExpect(status().is(201));
    }

    @Test
    public void validDeleteCharacterTest() throws Exception {
        when(service.deleteById(1L)).thenReturn(true);
        mockMvc.perform(delete("/characters/1"))
                .andExpect(status().is(204));
    }

    @Test
    public void getCharacterByIdExceptionTest() throws Exception {
        when(service.findById(10L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/characters/10")).andExpect(status().is(404));
    }

    @Test
    public void deleteCharacterByIdExceptionTest() throws Exception {
        when(service.deleteById(10L)).thenReturn(false);
        mockMvc.perform(delete("/characters/10")).andExpect(status().is(404));
    }

    @Test
    public void addCharacterAgeExceptionsTest() throws Exception {
        String ageAsChar = "{\"name\" : \"A New Hope\",\n" + "  \"age\" : \"w\",\n" + "\"characterType\":\"human\"}";
        String negativeAge = "{\"name\" : \"A New Hope\",\n" + "  \"age\" : \"-1\",\n" + "\"characterType\":\"human\"}";

        mockMvc.perform(post("/characters").content(ageAsChar).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(500))
                .andExpect(content().string(containsString("not a valid Integer value")));

        mockMvc.perform(post("/characters").content(negativeAge).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(422))
                .andExpect(content().string(containsString("Age must be positive")));
    }

    @Test
    public void addCharacterInvalidBooleanExceptionTest() throws Exception {
        String incorrectForceUserFormat = "{\"name\" : \"A New Hope\",\n" + "  \"age\" : 10,\n"
                + "  \"forceUser\" : \"w\", \n" + "\"characterType\":\"human\"}";
        mockMvc.perform(post("/characters").content(incorrectForceUserFormat).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(500))
                .andExpect(content().string(containsString("Cannot deserialize value of type `boolean`")));
    }

    @Test
    public void addCharacterNoNameExceptionTest() throws Exception {
        String incorrectForceUserFormat = "{" + "  \"age\" : 10,\n" + "  \"forceUser\" : false,\n"
                + "\"characterType\":\"human\"}";
        mockMvc.perform(post("/characters").content(incorrectForceUserFormat).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(422))
                .andExpect(content().string(containsString("Please provide a name")));
    }

}
