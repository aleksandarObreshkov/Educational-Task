package com.example.commands.add;

import com.example.app.clients.CharacterClient;
import com.example.app.commands.add.AddCharacterCommand;
import com.example.app.errors.InvalidInputException;
import com.example.model.dto.HumanDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AddCharacterCommandTest {

    private AddCharacterCommand command;

    @Mock
    private CharacterClient client;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        command = new AddCharacterCommand(client);
    }

    @Test
    public void validCharacterTest(){

        String[] arguments = new String[]{"add-character",
                "-n", "Name",
                "-a", "23",
                "-t", "human"};
        HumanDTO character = new HumanDTO();
        character.setName("Name");
        character.setAge(23);

        command.execute(arguments);
        verify(client).create(character);
    }

    @Test
    public void nameMissingTest(){

        String[] arguments = new String[]{"add-character",
                "-a", "23",
                "-t", "human"};

        Throwable thrownException = assertThrows(IllegalArgumentException.class, ()-> command.execute(arguments));
        assertEquals(thrownException.getMessage(), "Missing required option: n");
    }

    @Test
    public void ageMissingTest(){

        String[] arguments = new String[]{"add-character",
                "-n", "Name",
                "-t", "human"};

        Throwable thrownException = assertThrows(IllegalArgumentException.class, ()-> command.execute(arguments));
        assertEquals(thrownException.getMessage(), "Missing required option: a");
    }

    @Test
    public void characterTypeMissingTest(){

        String[] arguments = new String[]{"add-character",
                "-n", "Name",
                "-a", "23"};

        Throwable thrownException = assertThrows(IllegalArgumentException.class, ()-> command.execute(arguments));
        assertEquals(thrownException.getMessage(), "Missing required option: t");
    }

    @Test
    public void droidPrimaryFunctionMissingTest(){

        String[] arguments = new String[]{"add-character",
                "-n", "Name",
                "-a", "23",
                "-t", "droid" };
        Throwable thrownException = assertThrows(InvalidInputException.class, ()-> command.execute(arguments));
        assertEquals(thrownException.getMessage(), "Please specify the primary function of the droid.");
    }

    @Test
    public void incorrectIdsSyntaxTest(){

        String[] arguments = new String[]{"add-character",
                "-n", "Name",
                "-a", "23",
                "-t", "droid",
                "-ap", "1,2,3"};
        Throwable thrownException = assertThrows(InvalidInputException.class, ()-> command.execute(arguments));
        assertEquals(thrownException.getMessage(), "Incorrect format for ids: should be \"[<id>,<id>,...]\".");
    }

    @Test
    public void ageNotANumberTest(){

        String[] arguments = new String[]{"add-character",
                "-n", "Name",
                "-a", "w",
                "-t", "droid",
                "-ap", "1,2,3"};
        Throwable thrownException = assertThrows(InvalidInputException.class, ()-> command.execute(arguments));
        assertEquals(thrownException.getMessage(), "Age should be a number.");
    }
}
