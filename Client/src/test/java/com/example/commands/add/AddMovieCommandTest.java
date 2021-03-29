package com.example.commands.add;

import com.example.app.clients.MovieClient;
import com.example.app.commands.add.AddMovieCommand;
import com.example.app.errors.InvalidInputException;
import com.example.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

public class AddMovieCommandTest {

    private AddMovieCommand command;

    @Mock
    private MovieClient client;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        command = new AddMovieCommand(client);
    }

    @Test
    public void validMovieTest(){

        String[] arguments = new String[]{"add-movie",
                "-t", "Name",
                "-r", "9.8",
                "-d", "2020-12-12"};
        Movie movie = new Movie();
        movie.setTitle("Name");
        movie.setRating(9.8f);
        movie.setReleaseDate("2020-12-12");

        command.execute(arguments);
        verify(client).create(movie);
    }

    @Test
    public void titleMissingTest(){

        String[] arguments = new String[]{"add-movie",
                "-r", "9.8",
                "-d", "2020-12-12"};

        Throwable thrownException = assertThrows(IllegalArgumentException.class, ()-> command.execute(arguments));
        assertEquals(thrownException.getMessage(), "Missing required option: t");
    }

    @Test
    public void ratingMissingTest(){

        String[] arguments = new String[]{"add-movie",
                "-t", "Name",
                "-d", "2020-12-12"};

        Throwable thrownException = assertThrows(IllegalArgumentException.class, ()-> command.execute(arguments));
        assertEquals(thrownException.getMessage(), "Missing required option: r");
    }

    @Test
    public void releaseDateMissingTest(){

        String[] arguments = new String[]{"add-movie",
                "-t", "Name",
                "-r", "9.8"};

        Throwable thrownException = assertThrows(IllegalArgumentException.class, ()-> command.execute(arguments));
        assertEquals(thrownException.getMessage(), "Missing required option: d");
    }

    @Test
    public void incorrectDateFormat(){

        String[] arguments = new String[]{"add-movie",
                "-t", "Name",
                "-r", "9.8",
                "-d", "2020/12/12"};
        Throwable thrownException = assertThrows(InvalidInputException.class, ()-> command.execute(arguments));
        assertEquals(thrownException.getMessage(), "Incorrect date format: should be yyyy-MM-dd.");
    }

    @Test
    public void ratingNotFloatTest(){

        String[] arguments = new String[]{"add-movie",
                "-t", "Name",
                "-r", "w",
                "-d", "2020-12-12"};
        Throwable thrownException = assertThrows(InvalidInputException.class, ()-> command.execute(arguments));
        assertEquals(thrownException.getMessage(), "Rating should be float.");
    }
}
