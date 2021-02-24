package com.example.backend.demo.characterTests;

import repositories.EntityRepository;
import model.Droid;
import model.Human;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HumanValidRequestTest {

    @MockBean
    public EntityRepository repository;

    private static final Human human = new Human();

    @BeforeAll
    static void setUpHuman(){
        human.setId(10L);
        human.setName("Human");
        human.setAge(1);
        human.setForceUser(true);
    }

    @Test
    public void getDroidByIdTest() {
        when(repository.findById(10L, Human.class)).thenReturn(human);
        assertEquals(human, repository.findById(10L, Human.class));
    }

    @Test
    public void getAllDroidsTest() {
        List<Human> humans = new ArrayList<>();
        humans.add(human);
        when(repository.findAll(Human.class)).thenReturn(humans);
        assertEquals(humans, repository.findAll(Human.class));
    }

    @Test
    public void addHumanTest() {
        doAnswer((i) -> {
            assertEquals(i.getArgument(0), human);
            return null;
        }).when(repository).save(human);
        repository.save(human);
    }

    @Test
    public void deleteCharacterTest() {
        doAnswer((i) -> {
            assertEquals(i.getArgument(0), human.getId());
            return null;
        }).when(repository).deleteById(human.getId(), Droid.class);
        repository.deleteById(human.getId(), Droid.class);
    }
}
