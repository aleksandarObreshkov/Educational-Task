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
        template=new RestTemplate(); // TODO: Inconsistent use of "this."
    }

    @Override
    public void execute() throws Exception {
        try {
            Movie m = CreateEntityFunctions.createMovie(cmd); // TODO: Don't use abbreviations for variable names.
            template.postForObject(url, m, Movie.class);
        } catch (NumberFormatException e) { // TODO: This is also a very good example of handling exceptions in the wrong abstraction level. 
                                            // What if we add an integer to Movie - numberOfReviews? This exception could then mean that the rating
                                            // was not a valid float OR that the number of reviews was not a valid integer.
            throw new IllegalArgumentException("Rating should be float: "+e.getMessage(), e);
        } catch (DateTimeParseException e) { // TODO: Same.
            // TODO: Always preserve the original exception by giving it as a cause to the new exception. This will help you when you want to debug
            // an error, because you'll be able to see exactly where the error occurred:
            throw new DateTimeParseException("Wrong date format: "+e.getMessage(), e.getParsedString(),e.getErrorIndex());
        }
    }
}
