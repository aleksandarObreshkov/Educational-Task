package com.example.backend.demo;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BackendServerApplicationTests {

    @Autowired
    public MockMvc mockMvc;

    //empty test method?
    @Test
    void contextLoads() {
    }


    @Test
    public void exampleMockMvc() throws Exception{
        this.mockMvc.perform(get("/movies")).andExpect(status().isOk());
    }



}
