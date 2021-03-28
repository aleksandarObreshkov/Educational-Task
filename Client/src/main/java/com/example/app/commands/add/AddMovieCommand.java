package com.example.app.commands.add;

import com.example.app.clients.StarWarsClient;
import com.example.app.commands.Command;
import com.example.app.errors.InvalidInputException;
import com.example.model.Movie;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AddMovieCommand extends Command {

    private static final String TITLE_OPTION = "title";
    private static final String RATING_OPTION = "r";
    private static final String RELEASE_DATE_OPTION = "d";

    private static final String TITLE_OPTION_LONG = "title";
    private static final String RATING_OPTION_LONG = "rating";
    private static final String RELEASE_DATE_OPTION_LONG = "release-date";

    @Override
    public void execute(String[] arguments) {
        CommandLine cmd = parseCommandLine(getOptions(), arguments);
        Movie movie = createMovie(cmd);
        StarWarsClient.movies().create(movie);
    }

    @Override
    public String getDescription(){
        return "Add a movie to the database";
    }

    @Override
    public String getCommandString(){
        return "add-movie";
    }

    @Override
    public Options getOptions(){
        final Options options = new Options();
        Option title = Option.builder(TITLE_OPTION)
                .longOpt(TITLE_OPTION_LONG)
                .hasArg()
                .required()
                .type(String.class)
                .build();
        Option rating = Option.builder(RATING_OPTION)
                .longOpt(RATING_OPTION_LONG)
                .hasArg()
                .required()
                .desc("from 0 to 10.0")
                .type(Float.class)
                .build();

        Option releaseDate = Option.builder(RELEASE_DATE_OPTION)
                .longOpt(RELEASE_DATE_OPTION_LONG)
                .hasArg()
                .required()
                .type(LocalDate.class)
                .build();

        options.addOption(title);
        options.addOption(rating);
        options.addOption(releaseDate);

        return options;
    }

    private static Movie createMovie(CommandLine cmd) {
        try {
            Movie movie = new Movie();
            movie.setTitle(cmd.getOptionValue(TITLE_OPTION));
            movie.setRating(Float.parseFloat(cmd.getOptionValue(RATING_OPTION)));
            movie.setReleaseDate(cmd.getOptionValue(RELEASE_DATE_OPTION));
            return movie;
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Incorrect date format: should be yyyy-MM-dd.", e);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Rating should be float.", e);
        }
    }
}
