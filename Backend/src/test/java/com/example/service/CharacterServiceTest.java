package com.example.service;

import com.example.model.Droid;
import com.example.model.Human;
import com.example.model.dto.DroidDTO;
import com.example.model.dto.HumanDTO;
import com.example.services.CharacterService;
import com.example.spring_data_repositories.CharacterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CharacterServiceTest {

    private CharacterService service;

    private HumanDTO humanDTO;
    private Human human;

    private DroidDTO droidDTO;
    private Droid droid;

    @Mock
    private CharacterRepository characterRepository;

    private void initializeEntities(){
        human = new Human();
        human.setName("John");
        humanDTO = new HumanDTO();
        humanDTO.setName("John");

        droid = new Droid();
        droid.setName("Robot");
        droidDTO = new DroidDTO();
        droidDTO.setName("Robot");
    }

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        initializeEntities();
        service = new CharacterService(characterRepository, null, null, null);
    }

    @Test
    public void validHumanDtoRequestTest(){
        when(characterRepository.save(human)).thenReturn(human);
        service.save(humanDTO);
        verify(characterRepository).save(human);
    }

    @Test
    public void validDroidDtoRequestTest(){
        when(characterRepository.save(droid)).thenReturn(droid);
        service.save(droidDTO);
        verify(characterRepository).save(droid);
    }
}
