package com.example.app.commands;

import model.*;
import model.Character;
import org.apache.commons.cli.CommandLine;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.*;

public class EntityCreationUtils {

    public static Movie createMovie(CommandLine cmd)throws DateTimeParseException,NumberFormatException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Movie movie = new Movie(
                cmd.getOptionValue("t"),
                Float.parseFloat(cmd.getOptionValue("r")),
                LocalDate.parse(cmd.getOptionValue("d"),formatter)
        );
        return movie;
    }
    public static Character createCharacter(CommandLine cmd) throws NumberFormatException {
        String type = cmd.getOptionValue("t");
        if (type.equals("droid")){
            Droid droid = new Droid(
                    cmd.getOptionValue("n"),
                    Integer.parseInt(cmd.getOptionValue("a")),
                    false,
                    cmd.getOptionValue("pf"));
            if (cmd.hasOption("f")){
                droid.setForceUser(true);
            }
            return droid;
        }else{
            Human human = new Human(
                    cmd.getOptionValue("n"),
                    Integer.parseInt(cmd.getOptionValue("a")),
                    false);
            if (cmd.hasOption("f")) {
                human.setForceUser(true);
            }
            return human;
        }

    }
    public static Starship createStarship(CommandLine cmd)throws NumberFormatException{
        Starship newShip = new Starship(
                cmd.getOptionValue("n"),
                Float.parseFloat(cmd.getOptionValue("l"))
        );
        return newShip;
    }

}
