package com.example.backend.demo.characterTests;

import com.example.backend.demo.controllers.CharacterController;
import com.example.backend.demo.errors.ExceptionResolver;
import com.example.backend.demo.services.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CharacterController.class)
public class CharacterDataParsingTest {

    private MockMvc mockMvc;

    @MockBean
    private CharacterService service;

    @BeforeEach
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.standaloneSetup(new CharacterController(service))
                .setControllerAdvice(new ExceptionResolver())
                .build();
    }

    @Test
    public void getCharacterByIdExceptionTest() throws Exception {

        doThrow(new IOException("No such id")).when(service).getCharacterById("10");

        mockMvc.perform(get("/characters/10"))
                .andExpect(status().is(415))
                .andExpect(content().string(containsString("No such id")));
    }

    @Test
    public void deleteCharacterByIdExceptionTest() throws Exception {

        doThrow(new IOException("No such id")).when(service).deleteCharacterById("10");

        mockMvc.perform(delete("/characters/10"))
                .andExpect(status().is(415))
                .andExpect(content().string(containsString("No such id")));
    }

    @Test
    public void addCharacterAgeExceptionsTest() throws Exception {
        String ageAsChar = "{\"name\" : \"A New Hope\",\n" + "  \"age\" : w}";
        String negativeAge = "{\"name\" : \"A New Hope\",\n" + "  \"age\" : -1}";
        mockMvc.perform(post("/characters").
                content(ageAsChar).
                contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(415))
                    .andExpect(content().string(containsString("Unrecognized token 'w'")));

        mockMvc.perform(post("/characters").
                content(negativeAge).
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(415))
                .andExpect(content().string(containsString("Age must be positive")));
    }

    @Test
    public void addCharacterInvalidBooleanExceptionTest() throws Exception {
        String incorrectForceUserFormat = "{\"name\" : \"A New Hope\",\n" +
                "  \"age\" : 10,\n" +
                "  \"forceUser\" : w\n" +
                "}";
        mockMvc.perform(post("/characters").
                content(incorrectForceUserFormat).
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(415))
                .andExpect(content().string(containsString("Unrecognized token 'w'")));
    }

    @Test
    public void addCharacterNoNameExceptionTest() throws Exception {
        String incorrectForceUserFormat = "{" +
                "  \"age\" : 10,\n" +
                "  \"forceUser\" : false\n" +
                "}";
        mockMvc.perform(post("/characters").
                content(incorrectForceUserFormat).
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(415))
                .andExpect(content().string(containsString("Please provide a name")));
    }
}
