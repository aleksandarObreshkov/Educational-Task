package com.example.characterTests;

import com.example.model.Character;
import com.example.spring_data_repositories.CharacterRepository;
import com.example.model.Droid;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DroidValidRequestTest {

    @MockBean
    public CharacterRepository repository;

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
        when(repository.findById(10L)).thenReturn(Optional.of(droid));
        assertEquals(Optional.of(droid), repository.findById(10L));
    }

    @Test
    public void getAllDroidsTest() {
        List<Character> droids = new ArrayList<>();
        droids.add(droid);
        when(repository.findAll()).thenReturn(droids);
        assertEquals(droids, repository.findAll());
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
        when(repository.deleteCharacterById(10L)).thenReturn(1);
        assertEquals(1, repository.deleteCharacterById(10L));
    }
}
