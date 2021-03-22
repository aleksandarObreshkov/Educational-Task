package com.example.backend.resolver.character;

import com.example.model.Human;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import com.example.repositories.CharacterRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class HumanResolverTest {

    @Mock
    @Qualifier("characterRepository")
    private CharacterRepository repository;

    private HumanResolver resolver;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        resolver = new HumanResolver(repository);
    }

    @Test
    public void allHumans(){
        List<Human> humans = new ArrayList<>();
        when(repository.findAll(Human.class)).thenReturn(humans);
        assertEquals(resolver.allHumans(), humans);
    }

    @Test
    public void human(){
        Human human = Mockito.mock(Human.class);
        when(repository.findById(10L, Human.class)).thenReturn(human);
        assertEquals(resolver.human(10L), Optional.of(human));
    }
}
