package com.example.app.commands;

import com.example.app.model.Character;
import com.example.app.model.Movie;
import com.example.app.model.Starship;
import org.apache.commons.cli.CommandLine;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.*;
import java.util.UUID;

public class CreateEntityFunctions {

    public static Movie createMovie(CommandLine cmd)throws DateTimeParseException,NumberFormatException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Movie movie = new Movie(
                UUID.randomUUID().toString(),
                cmd.getOptionValue("t"),
                LocalDate.parse(cmd.getOptionValue("d"),formatter),
                Float.parseFloat(cmd.getOptionValue("r"))
        );
        return movie;
    }
    public static Character createCharacter(CommandLine cmd)throws NumberFormatException{
        Character newCharacter = new Character(
                UUID.randomUUID().toString(),
                cmd.getOptionValue("name"),
                Integer.parseInt(cmd.getOptionValue("age")),
                false);
        if (cmd.hasOption("force-user")) newCharacter.setForceUser(true);

        return newCharacter;
    }
    public static Starship createStarship(CommandLine cmd)throws NumberFormatException{
        Starship newShip = new Starship(
                UUID.randomUUID().toString(),
                cmd.getOptionValue("n"),
                Float.parseFloat(cmd.getOptionValue("l"))
        );
        return newShip;
    }

}
