package com.example.app.commands;

import model.Movie;
import org.apache.commons.cli.CommandLine;
import org.springframework.web.client.RestTemplate;
import java.time.format.DateTimeParseException;

public class AddMovieCommand implements Command {

    private final String url;
    private final CommandLine cmd;
    private final RestTemplate template;

    public AddMovieCommand(CommandLine cmd, String url){
        this.cmd=cmd;
        this.url = url;
        template=new RestTemplate();
    }

    @Override
    public void execute() throws Exception {
        try {
            Movie m = CreateEntityFunctions.createMovie(cmd);
            template.postForObject(url, m, Movie.class);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Rating should be float: "+e.getMessage(), e);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Wrong date format: "+e.getMessage(), e.getParsedString(),e.getErrorIndex());
        }
    }
}
