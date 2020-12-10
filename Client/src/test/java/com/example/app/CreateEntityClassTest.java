package com.example.app;

import com.example.app.commands.CreateEntityFunctions;
import model.Character;
import model.Movie;
import model.Starship;
import org.apache.commons.cli.CommandLine;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

public class CreateEntityClassTest {

    @Mock
    CommandLine cmd;

    @Test
    public void createMovieTest(){
        String title="Nemo";
        String date = "2020-12-12";
        String rating = "3.12";

        cmd=Mockito.mock(CommandLine.class);

        Mockito.when(cmd.getOptionValue("t")).thenReturn(title);
        Mockito.when(cmd.getOptionValue("d")).thenReturn(date);
        Mockito.when(cmd.getOptionValue("r")).thenReturn(rating);

        Movie toTest=CreateEntityFunctions.createMovie(cmd);
        assertEquals(title, toTest.getTitle());
        assertEquals(date,toTest.getReleaseDate()+"");
        assertEquals(rating, toTest.getRating()+"");

    }

    @Test
    public void createStarshipTest(){
        String name="Ship";
        String length="8.9";

        cmd=Mockito.mock(CommandLine.class);

        Mockito.when(cmd.getOptionValue("n")).thenReturn(name);
        Mockito.when(cmd.getOptionValue("l")).thenReturn(length);

        Starship toTest=CreateEntityFunctions.createStarship(cmd);
        assertEquals(name, toTest.getName());
        assertEquals(length,toTest.getLength()+"");

    }

    @Test
    public void createCharacterTest(){
        String name="Ship";
        String age="89";
        String forceUser="false";

        cmd=Mockito.mock(CommandLine.class);

        Mockito.when(cmd.getOptionValue("n")).thenReturn(name);
        Mockito.when(cmd.getOptionValue("a")).thenReturn(age);
        Mockito.when(cmd.getOptionValue("f")).thenReturn(forceUser);

        Character toTest=CreateEntityFunctions.createCharacter(cmd);
        assertEquals(name, toTest.getName());
        assertEquals(age,toTest.getAge()+"");
        assertEquals(forceUser,toTest.isForceUser()+"");

    }

    @Test
    public void createMovieRatingNumberFormatTest(){
        String rating="www";
        String date = "2020-12-12";
        String title = "title";

        cmd=Mockito.mock(CommandLine.class);

        Mockito.when(cmd.getOptionValue("d")).thenReturn(date);
        Mockito.when(cmd.getOptionValue("t")).thenReturn(title);
        Mockito.when(cmd.getOptionValue("r")).thenReturn(rating);
        assertThrows(NumberFormatException.class, ()->{CreateEntityFunctions.createMovie(cmd);});
    }

    @Test
    public void createMovieDateTimeFormatTest(){
        String rating="3.2";
        String date = "2020/12/12";
        String title = "title";

        cmd=Mockito.mock(CommandLine.class);

        Mockito.when(cmd.getOptionValue("d")).thenReturn(date);
        Mockito.when(cmd.getOptionValue("t")).thenReturn(title);
        Mockito.when(cmd.getOptionValue("r")).thenReturn(rating);
        assertThrows(DateTimeParseException.class, ()->{CreateEntityFunctions.createMovie(cmd);});
    }

    @Test
    public void createMovieExceptionOrderTest(){
        String rating="www";
        String date = "2020/12/12";
        String title = "title";

        cmd=Mockito.mock(CommandLine.class);

        Mockito.when(cmd.getOptionValue("t")).thenReturn(title);
        Mockito.when(cmd.getOptionValue("d")).thenReturn(date);
        Mockito.when(cmd.getOptionValue("r")).thenReturn(rating);

        assertThrows(DateTimeParseException.class, ()->{CreateEntityFunctions.createMovie(cmd);});
    }

    @Test
    public void createCharacterExceptionTest(){
        String name="Ship";
        String age="ww";
        String forceUser="false";

        cmd=Mockito.mock(CommandLine.class);

        Mockito.when(cmd.getOptionValue("n")).thenReturn(name);
        Mockito.when(cmd.getOptionValue("a")).thenReturn(age);
        Mockito.when(cmd.getOptionValue("f")).thenReturn(forceUser);


        assertThrows(NumberFormatException.class,()->{CreateEntityFunctions.createCharacter(cmd);});

    }

    @Test
    public void createStarshipExceptionTest1(){
        String name="Ship";
        String length="ww";

        cmd=Mockito.mock(CommandLine.class);

        Mockito.when(cmd.getOptionValue("n")).thenReturn(name);
        Mockito.when(cmd.getOptionValue("l")).thenReturn(length);

        assertThrows(NumberFormatException.class,()->{CreateEntityFunctions.createCharacter(cmd);});

    }

}
