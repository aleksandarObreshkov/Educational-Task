package com.example.app.commands;

import model.*;
import model.Character;
import org.apache.commons.cli.CommandLine;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.*;

//this is more of a utility class
//TODO: rename to something like "EntityCreationUtils"
public class CreateEntityFunctions {

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
            Droid d = new Droid(
                    cmd.getOptionValue("n"),
                    Integer.parseInt(cmd.getOptionValue("a")),
                    false,
                    cmd.getOptionValue("pf"));
            if (cmd.hasOption("f")) d.setForceUser(true); //this line is hard to read
            return d;
        }
        //this isn't good practice with if else statements formatting
        //the convention is
        /*
            if (...) {
                ...
            } else {
                ...
            }
         */
        else{
            Human h = new Human(
                    cmd.getOptionValue("n"),
                    Integer.parseInt(cmd.getOptionValue("a")),
                    false);
            if (cmd.hasOption("f")) h.setForceUser(true); //so is this
            return h;
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
