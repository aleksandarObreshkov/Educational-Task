package com.example.characterTests;

import com.example.controllers.CharacterController;
import com.example.errors.ExceptionResolver;
import com.example.services.CharacterService;
import com.example.spring_data_repositories.CharacterRepository;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CharacterDataParsingTest {

    private MockMvc mockMvc;

    @Mock
    private CharacterRepository repository;

    @Mock
    private CharacterService service;


    @BeforeEach
    public void setupMockMvc(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new CharacterController(repository, service))
                .setControllerAdvice(new ExceptionResolver())
                .build();
    }

    @Test
    public void getCharacterByIdExceptionTest() throws Exception {
        when(repository.findById(10L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/characters/10"))
                .andExpect(status().is(404));
    }

    @Test
    public void deleteCharacterByIdExceptionTest() throws Exception {
        when(service.deleteById(10L)).thenReturn(false);
        mockMvc.perform(delete("/characters/10"))
                .andExpect(status().is(404));
    }

    @Test
    public void addCharacterAgeExceptionsTest() throws Exception {
        String ageAsChar = "{\"name\" : \"A New Hope\",\n" + "  \"age\" : \"w\",\n"+"\"characterType\":\"human\"}";
        String negativeAge = "{\"name\" : \"A New Hope\",\n" + "  \"age\" : \"-1\",\n"+"\"characterType\":\"human\"}";

        mockMvc.perform(post("/characters").
                content(ageAsChar).
                contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(500))
                    .andExpect(
                            content().string(containsString("not a valid Integer value")));



        mockMvc.perform(post("/characters").
                content(negativeAge).
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(422))
                .andExpect(content().string(containsString("Age must be positive")));
    }

    @Test
    public void addCharacterInvalidBooleanExceptionTest() throws Exception {
        String incorrectForceUserFormat = "{\"name\" : \"A New Hope\",\n" +
                "  \"age\" : 10,\n" +
                "  \"forceUser\" : \"w\", \n" +
                "\"characterType\":\"human\"}";
        mockMvc.perform(post("/characters").
                content(incorrectForceUserFormat).
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(500))
                .andExpect(content().string(containsString("Cannot deserialize value of type `boolean`")));
    }

    @Test
    public void addCharacterNoNameExceptionTest() throws Exception {
        String incorrectForceUserFormat = "{" +
                "  \"age\" : 10,\n" +
                "  \"forceUser\" : false,\n" +
                "\"characterType\":\"human\"}";
        mockMvc.perform(post("/characters").
                content(incorrectForceUserFormat).
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(422))
                .andExpect(content().string(containsString("Please provide a name")));
    }
}
