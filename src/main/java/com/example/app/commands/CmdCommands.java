package com.example.app.commands;

import com.example.app.domain.Character;
import com.example.app.domain.Movie;
import com.example.app.domain.Starship;
import org.apache.commons.cli.CommandLine;


import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.*;
import java.util.List;
import java.util.UUID;

public class CmdCommands{


    public static Movie createMovie(CommandLine cmd)throws DateTimeParseException,NumberFormatException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
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

    public static void deleteMovie(String idToDelete, List<Movie> movies){
        int id = 0;
        for (Movie a : movies) {
            if (a.getId().equals(idToDelete)) {
                id = movies.indexOf(a);
                movies.remove(id);
                break;
            }
        }
    }
    public static void deleteCharacter(String idToDelete, List<Character> characters){

        int id = 0;
        for (Character a : characters) {
            if (a.getId().equals(idToDelete)) {
                id = characters.indexOf(a);
                characters.remove(id);
                break;
            }
        }
    }
    public static void deleteStarship(String idToDelete, List<Starship> starships){
        int id = 0;
        for (Starship a : starships) {
            if (a.getId().equals(idToDelete)) {
                id = starships.indexOf(a);
                starships.remove(id);
                break;
            }
        }
    }


}
