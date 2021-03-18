package com.example.app;

import com.example.app.utils.EntityCreationUtils;
import com.example.app.errors.InvalidInputException;
import model.Character;
import model.Movie;
import model.Starship;
import org.apache.commons.cli.CommandLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CreateEntityClassTest {

    @Mock
    CommandLine cmd;

    @BeforeEach
    public void setupCmd(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createMovieTest(){
        String title="Nemo";
        String date = "2020-12-12";
        String rating = "3.12";

        when(cmd.getOptionValue("t")).thenReturn(title);
        when(cmd.getOptionValue("d")).thenReturn(date);
        when(cmd.getOptionValue("r")).thenReturn(rating);

        Movie toTest= EntityCreationUtils.createMovie(cmd);
        assertEquals(title, toTest.getTitle());
        assertEquals(date,toTest.getReleaseDate()+"");
        assertEquals(rating, toTest.getRating()+"");
    }

    @Test
    public void createStarshipTest(){
        String name="Ship";
        String length="8.9";

        when(cmd.getOptionValue("n")).thenReturn(name);
        when(cmd.getOptionValue("l")).thenReturn(length);

        Starship toTest= EntityCreationUtils.createStarship(cmd);
        assertEquals(name, toTest.getName());
        assertEquals(length,toTest.getLength()+"");
    }

    @Test
    public void createCharacterTest(){
        String name="Ship";
        String age="89";
        String forceUser="false";
        String type = "droid";

        when(cmd.getOptionValue("n")).thenReturn(name);
        when(cmd.getOptionValue("a")).thenReturn(age);
        when(cmd.getOptionValue("f")).thenReturn(forceUser);
        when(cmd.getOptionValue("t")).thenReturn(type);

        Character toTest= EntityCreationUtils.createCharacter(cmd);
        assertEquals(name, toTest.getName());
        assertEquals(age,toTest.getAge()+"");
        assertEquals(forceUser,toTest.isForceUser()+"");
        assertEquals(type, toTest.getCharacterType());
    }

    @Test
    public void createMovieRatingNumberFormatTest(){
        String rating="www";
        String date = "2020-12-12";
        String title = "title";

        when(cmd.getOptionValue("d")).thenReturn(date);
        when(cmd.getOptionValue("t")).thenReturn(title);
        when(cmd.getOptionValue("r")).thenReturn(rating);

        Exception exception = assertThrows(InvalidInputException.class, ()->{
            EntityCreationUtils.createMovie(cmd);});
        assertEquals(exception.getMessage(), "Rating should be float.");
    }

    @Test
    public void createMovieDateTimeFormatTest(){
        String rating="3.2";
        String date = "2020/12/12";
        String title = "title";

        when(cmd.getOptionValue("d")).thenReturn(date);
        when(cmd.getOptionValue("t")).thenReturn(title);
        when(cmd.getOptionValue("r")).thenReturn(rating);

        Exception exception = assertThrows(InvalidInputException.class, ()->{
            EntityCreationUtils.createMovie(cmd);});
        assertEquals(exception.getMessage(), "Incorrect date format.");
    }

    @Test
    public void createMovieExceptionOrderTest(){
        String rating="www";
        String date = "2020/12/12";
        String title = "title";

        when(cmd.getOptionValue("t")).thenReturn(title);
        when(cmd.getOptionValue("d")).thenReturn(date);
        when(cmd.getOptionValue("r")).thenReturn(rating);

        Exception exception = assertThrows(InvalidInputException.class, ()->{
            EntityCreationUtils.createMovie(cmd);});
        assertEquals(exception.getCause().getMessage(), "For input string: \"www\"");
    }

    @Test
    public void createCharacterExceptionTest(){
        String name="Ship";
        String age="ww";
        String forceUser="false";
        String characterType = "droid";

        when(cmd.getOptionValue("n")).thenReturn(name);
        when(cmd.getOptionValue("a")).thenReturn(age);
        when(cmd.getOptionValue("f")).thenReturn(forceUser);
        when(cmd.getOptionValue("t")).thenReturn(characterType);

        Exception exception = assertThrows(InvalidInputException.class,()->{
            EntityCreationUtils.createCharacter(cmd);});
        assertEquals(exception.getMessage(), "Age should be a number.");
    }

    @Test
    public void createStarshipExceptionTest1(){
        String name="Ship";
        String length="ww";

        when(cmd.getOptionValue("n")).thenReturn(name);
        when(cmd.getOptionValue("l")).thenReturn(length);

        Exception exception = assertThrows(InvalidInputException.class,()->{
            EntityCreationUtils.createStarship(cmd);});
        assertEquals(exception.getMessage(), "Length should be float.");
    }
}
