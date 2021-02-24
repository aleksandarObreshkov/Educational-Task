package com.example.backend.demo.characterTests;

import repositories.EntityRepository;
import model.Droid;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DroidValidRequestTest {

    @MockBean
    public EntityRepository repository;

    private static final Droid droid = new Droid();

    @BeforeAll
    static void setUpDroid(){
        droid.setId(10L);
        droid.setName("Droid");
        droid.setAge(1);
        droid.setForceUser(false);
        droid.setPrimaryFunction("primaryFunction");
    }

    @Test
    public void getDroidByIdTest() {
        when(repository.findById(10L, Droid.class)).thenReturn(droid);
        assertEquals(droid, repository.findById(10L, Droid.class));
    }

    @Test
    public void getAllDroidsTest() {
        List<Droid> droids = new ArrayList<>();
        droids.add(droid);
        when(repository.findAll(Droid.class)).thenReturn(droids);
        assertEquals(droids, repository.findAll(Droid.class));
    }

    @Test
    public void addHumanTest() {
        doAnswer((i) -> {
            assertEquals(i.getArgument(0), droid);
            return null;
        }).when(repository).save(droid);
        repository.save(droid);
    }

    @Test
    public void deleteCharacterTest() {
        when(repository.deleteById(10L,Droid.class)).thenReturn(true);
        assertTrue(repository.deleteById(10L, Droid.class));
    }


}
