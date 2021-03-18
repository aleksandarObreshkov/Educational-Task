package com.example.app.commands.addCommands;

import com.example.app.commands.Command;
import com.example.app.errors.RestTemplateResponseErrorHandler;
import com.example.app.utils.EntityCreationUtils;
import model.Movie;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;

public class AddMovieCommand implements Command {

    private final String url;
    private final CommandLine cmd;
    private final RestTemplate template;

    public AddMovieCommand(CommandLine cmd, String url){
        this.cmd=cmd;
        this.url = url;
        this.template = new RestTemplateBuilder()
                .errorHandler(new RestTemplateResponseErrorHandler()).build();
    }

    @Override
    public void execute() {
        Movie movie = EntityCreationUtils.createMovie(cmd);
        template.postForObject(url, movie, Movie.class);
    }

    public static String getDescription(){
        return "Add a movie to the database";
    }

    public static String getCommandString(){
        return "add-movie";
    }

    public static Options getAddMovieOptions(){
        final Options options = new Options();
        Option title = Option.builder("t")
                .longOpt("title")
                .hasArg()
                .required()
                .type(String.class)
                .build();
        // TODO Indicate in some way that the rating must be a floating point number between 0 and 10.
        Option rating = Option.builder("r")
                .longOpt("rating")
                .hasArg()
                .required()
                .type(Float.class)
                .build();

        Option releaseDate = Option.builder("d")
                .longOpt("release-date")
                .hasArg()
                .required()
                .type(LocalDate.class)
                .build();

        options.addOption(title);
        options.addOption(rating);
        options.addOption(releaseDate);

        return options;
    }
}
