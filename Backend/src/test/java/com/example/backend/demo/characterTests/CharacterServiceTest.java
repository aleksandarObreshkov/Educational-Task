package com.example.backend.demo.characterTests;

import com.example.backend.demo.controllers.CharacterController;
import model.Character;
import com.example.backend.demo.services.CharacterService;
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
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(CharacterController.class)
public class CharacterServiceTest {

    /*
    @Autowired
    private MockMvc mvc;

    @MockBean
    public CharacterService service;


    private ObjectMapper mapper;

    public CharacterServiceTest(Character character) {
        this.character = character;
    }

    @BeforeEach
    public void setupObjectMapper(){
        mapper=new ObjectMapper();
    }

    private final Character character;

    @Test
    public void getCharacterByIdTest() throws Exception {
        when(service.getCharacterById(10L)).thenReturn(character);
        mvc.perform(get("/characters/1")).andExpect(content().json(mapper.writeValueAsString(character)));
    }

    @Test
    public void getAllCharactersTest() throws Exception {
        List<Character> characters = new ArrayList<>();
        characters.add(character);
        when(service.getAll()).thenReturn(characters);
        mvc.perform(get("/characters")).andExpect(content().json(mapper.writeValueAsString(characters)));
    }

    @Test
    public void addCharacterTest() throws Exception {
        doAnswer((i) -> {
            assertEquals(i.getArgument(0), character);
            return null;
        }).when(service).addCharacter(character);
        service.addCharacter(character);
    }

    @Test
    public void deleteCharacterTest() throws Exception {
        doAnswer((i) -> {
            assertEquals(i.getArgument(0), character.getId());
            return null;
        }).when(service).deleteCharacterById(character.getId());
        service.deleteCharacterById(character.getId());
    }


     */

}
