package com.example.commands.add;

import com.example.app.clients.StarshipClient;
import com.example.app.commands.add.AddStarshipCommand;
import com.example.app.errors.InvalidInputException;
import com.example.model.Starship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

public class AddStarshipCommandTest {

    private AddStarshipCommand command;

    @Mock
    private StarshipClient client;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        command = new AddStarshipCommand(client);
    }

    @Test
    public void validStarshipTest(){
        String[] arguments = new String[]{"add-starship",
                "-n", "Name",
                "-l", "9.8"};
        Starship starship = new Starship();
        starship.setName("Name");
        starship.setLengthInMeters(9.8f);
        command.execute(arguments);
        verify(client).create(starship);
    }

    @Test
    public void nameMissingTest(){

        String[] arguments = new String[]{"add-starship",
                "-l", "9.8"};

        Throwable thrownException = assertThrows(IllegalArgumentException.class, ()-> command.execute(arguments));
        assertEquals(thrownException.getMessage(), "Missing required option: n");
    }

    @Test
    public void lengthMissingTest(){
        String[] arguments = new String[]{"add-starship",
                "-n", "Name"};

        Throwable thrownException = assertThrows(IllegalArgumentException.class, ()-> command.execute(arguments));
        assertEquals(thrownException.getMessage(), "Missing required option: l");
    }
    @Test
    public void unrecognisedUnitOfMeasurementTest(){
        String[] arguments = new String[]{"add-starship",
                "-n", "Name",
                "-l", "9.8",
                "-u", "inches"};

        Throwable thrownException = assertThrows(InvalidInputException.class, ()-> command.execute(arguments));
        assertEquals(thrownException.getMessage(), "Unrecognised unit of measurement: inches");
    }
}
