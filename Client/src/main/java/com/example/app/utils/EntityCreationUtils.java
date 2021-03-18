package com.example.app.utils;

import com.example.app.errors.InvalidInputException;
import model.*;
import model.Character;
import org.apache.commons.cli.CommandLine;
import java.time.format.DateTimeParseException;

// TODO The methods here belong in their respective commands. The knowledge of what options represent what fields ("t"
// being title, for example) is currently fragmented between this class and the different "add" commands.
public class EntityCreationUtils {

    public static Movie createMovie(CommandLine cmd) {
        try {
            Movie movie = new Movie();
            movie.setTitle(cmd.getOptionValue("t"));
            movie.setRating(Float.parseFloat(cmd.getOptionValue("r")));
            movie.setReleaseDate(cmd.getOptionValue("d"));
            return movie;
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Incorrect date format.", e.getCause()); // TODO Why e.getCause() instead of
                                                                                     // just e?
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Rating should be float.", e);
        }
    }

    public static Character createCharacter(CommandLine cmd) {
        try {
            String type = cmd.getOptionValue("t");
            if (type.equals("droid")) {
                // TODO The body of this "if" can be its own separate method and the same applies for the body of the "else" block.
                Droid droid = new Droid();
                droid.setName(cmd.getOptionValue("n"));
                droid.setAge(Integer.parseInt(cmd.getOptionValue("a")));
                droid.setForceUser(false);
                droid.setPrimaryFunction(cmd.getOptionValue("pf"));
                if (cmd.hasOption("f")) {
                    droid.setForceUser(true);
                }
                return droid;
            } else {
                Human human = new Human();
                human.setName(cmd.getOptionValue("n"));
                human.setAge(Integer.parseInt(cmd.getOptionValue("a")));
                human.setForceUser(false);
                if (cmd.hasOption("f")) {
                    human.setForceUser(true);
                }
                return human;
            }
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Age should be a number.", e);
        }

    }

    public static Starship createStarship(CommandLine cmd) {
        try {
            return new Starship(cmd.getOptionValue("n"), Float.parseFloat(cmd.getOptionValue("l")));
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Length should be float.", e);
        }
    }

}
