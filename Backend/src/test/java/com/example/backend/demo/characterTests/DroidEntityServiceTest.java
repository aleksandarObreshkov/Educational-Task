package com.example.backend.demo.characterTests;

import com.example.backend.demo.services.EntityService;
import model.Droid;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DroidEntityServiceTest {

    @MockBean
    public EntityService service;

    private static final Droid droid = new Droid();

    @BeforeAll
    static void setupObjectMapper(){
        droid.setId(10L);
        droid.setName("Droid");
        droid.setAge(1);
        droid.setForceUser(false);
        droid.setPrimaryFunction("primaryFunction");
    }

    @Test
    public void getDroidByIdTest() {
        when(service.getById(10L, Droid.class)).thenReturn(ResponseEntity.ok(droid));
        assertEquals(ResponseEntity.ok(droid),service.getById(10L, Droid.class));
    }

    @Test
    public void getAllDroidsTest() {
        List<Droid> droids = new ArrayList<>();
        droids.add(droid);
        when(service.getAll(Droid.class)).thenReturn(ResponseEntity.ok(droids));
        assertEquals(ResponseEntity.ok(droids), service.getAll(Droid.class));
    }

    @Test
    public void addCharacterTest() {
        when(service.getAll(Droid.class)).thenReturn(ResponseEntity.status(HttpStatus.OK).build());
        assertEquals(ResponseEntity.status(HttpStatus.OK).build(), service.getAll(Droid.class));
    }

    @Test
    public void deleteCharacterTest() {
        when(service.deleteById(10L,Droid.class)).thenReturn(ResponseEntity.status(HttpStatus.OK).build());
        assertEquals(ResponseEntity.status(HttpStatus.OK).build(), service.deleteById(10L,Droid.class));
    }


}
