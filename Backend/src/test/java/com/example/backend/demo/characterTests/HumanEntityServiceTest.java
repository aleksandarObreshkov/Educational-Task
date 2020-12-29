package com.example.backend.demo.characterTests;

import com.example.backend.demo.services.EntityService;
import model.Droid;
import model.Human;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HumanEntityServiceTest {

    @MockBean
    public EntityService service;

    private static final Human human = new Human();

    @BeforeAll
    static void setupHumanObject(){
        human.setId(10L);
        human.setName("Human");
        human.setAge(1);
        human.setForceUser(true);
    }

    @Test
    public void getDroidByIdTest() {
        when(service.getById(10L, Human.class)).thenReturn(ResponseEntity.ok(human));
        assertEquals(ResponseEntity.ok(human),service.getById(10L, Human.class));
    }

    @Test
    public void getAllDroidsTest() {
        List<Human> humans = new ArrayList<>();
        humans.add(human);
        when(service.getAll(Human.class)).thenReturn(ResponseEntity.ok(humans));
        assertEquals(ResponseEntity.ok(humans), service.getAll(Human.class));
    }

    @Test
    public void addCharacterTest() {
        doAnswer((i) -> {
            assertEquals(i.getArgument(0), human);
            return null;
        }).when(service).add(human);
        service.add(human);
    }

    @Test
    public void deleteCharacterTest() {
        doAnswer((i) -> {
            assertEquals(i.getArgument(0), human.getId());
            return null;
        }).when(service).deleteById(human.getId(), Droid.class);
        service.deleteById(human.getId(), Droid.class);
    }
}
