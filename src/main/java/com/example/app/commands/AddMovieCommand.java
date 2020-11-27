package com.example.app.commands;

import com.example.app.FileCreator;
import com.example.app.HelperMethods;
import com.example.app.domain.Movie;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AddMovieCommand implements Command {

    private FileCreator fileCreator = new FileCreator();
    private List<Movie> movies;
    private CommandLine cmd;


    public AddMovieCommand(CommandLine cmd) throws IOException {
        this.cmd=cmd;
        this.movies= HelperMethods.getDataFromFile(fileCreator.getFileMovies(), Movie.class);
    }

    @Override
    public void execute() throws Exception {
        try {
            movies.add(CmdCommands.createMovie(cmd));
            HelperMethods.writeDataToFile(movies, fileCreator.getFileMovies());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Rating should be float: "+e.getMessage(), e);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Wrong date format: "+e.getMessage(), e.getParsedString(),e.getErrorIndex());
        }
    }
}
