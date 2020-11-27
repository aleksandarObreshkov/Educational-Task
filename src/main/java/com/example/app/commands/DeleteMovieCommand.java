package com.example.app.commands;

import com.example.app.FileCreator;
import com.example.app.HelperMethods;
import com.example.app.domain.Movie;

import java.util.List;

public class DeleteMovieCommand implements Command {

    private String id;
    private List<Movie> movies;
    private final FileCreator fileCreator = new FileCreator();

    public DeleteMovieCommand(String id) {
        this.id = id;
    }

    @Override
    public void execute() throws Exception {
        movies = HelperMethods.getDataFromFile(fileCreator.getFileMovies(), Movie.class);
        CmdCommands.deleteMovie(id, movies);
        HelperMethods.writeDataToFile(movies, fileCreator.getFileMovies());
    }
}
