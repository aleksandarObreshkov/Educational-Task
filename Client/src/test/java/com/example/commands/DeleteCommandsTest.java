package com.example.commands;

import com.example.app.commands.delete.DeleteCharacterCommand;
import com.example.app.commands.delete.DeleteMovieCommand;
import com.example.app.commands.delete.DeleteStarshipCommand;
import com.example.app.errors.InvalidInputException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

public class DeleteCommandsTest {



    @Test
    public void missingMovieIdTest(){
        DeleteMovieCommand movieCommand = new DeleteMovieCommand();
        String[] arguments = new String[]{"delete-movie", "-id", "102"};
        Throwable thrownException = assertThrows(IllegalArgumentException.class, ()->movieCommand.execute(arguments));
        assertEquals(thrownException.getMessage(), "Missing required option: id");
    }

    @Test
    public void missingStarshipIdTest(){
        DeleteStarshipCommand starshipCommand = new DeleteStarshipCommand();
        String[] arguments = new String[]{"delete-starship", "-id", "102"};
        Throwable thrownException = assertThrows(IllegalArgumentException.class, ()->starshipCommand.execute(arguments));
        assertEquals(thrownException.getMessage(), "Missing required option: id");
    }

}
