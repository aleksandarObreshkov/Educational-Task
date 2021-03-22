package com.example.backend;

import com.example.controllers.CharacterController;
import com.example.errors.ExceptionResolver;
import com.example.model.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.example.repositories.EntityRepository;
import javax.persistence.PersistenceException;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CharacterController.class)
public class PersistenceExceptionTest {

    @MockBean
    @Qualifier("entityRepository")
    public EntityRepository repository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setupMockMvc(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new CharacterController(repository))
                .setControllerAdvice(new ExceptionResolver())
                .build();
    }

    @Test
    public void persistenceExceptionHandling() throws Exception {
        when(repository.deleteById(10L, Character.class)).thenThrow(new PersistenceException("Database error."));
        mockMvc.perform(delete("/characters/10"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Database error")));
    }
}
