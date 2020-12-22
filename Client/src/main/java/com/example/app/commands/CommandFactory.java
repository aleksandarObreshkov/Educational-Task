package com.example.app.commands;

import com.example.app.errors.InvalidInputException;
import org.apache.commons.cli.*;
import java.util.Arrays;

public class CommandFactory {

    public Command createCommand(String[] arguments) throws ParseException {

        final String url = "http://localhost:8080/";
        String mainCommand = arguments[0];
        String[] commandOptions = Arrays.copyOfRange(arguments, 1, arguments.length);
        CommandLine cmd; //TODO reduce the scope of this variable
        switch (mainCommand) {

            case "characters": return new ShowCharactersCommand(url+mainCommand);
            case "movies": return new ShowMoviesCommand(url+mainCommand);
            case "starships":  return new ShowStarshipsCommand(url+mainCommand);
            case "add-movie": {
                cmd = parseCommandLine(AddMovieCommand.getAddMovieOptions(),commandOptions);
                return new AddMovieCommand(cmd, url+"movies");
            }
            case "add-character": {
                cmd = parseCommandLine(AddCharacterCommand.getAddCharacterOptions(),commandOptions);
                return new AddCharacterCommand(cmd, url+"characters");
            }
            case "add-starship": {
                cmd = parseCommandLine(AddStarshipCommand.getAddStarshipOptions(),commandOptions);
                return  new AddStarshipCommand(cmd, url+"starships");
            }
            case "delete-character":{
                cmd = parseCommandLine(DeleteCharacterCommand.getDeleteOptions(),commandOptions);
                String newUrl = url+"characters/"+cmd.getOptionValue("id");
                return new DeleteCharacterCommand(newUrl);
            }
            case "delete-starship": {
                cmd = parseCommandLine(DeleteStarshipCommand.getDeleteOptions(),commandOptions);
                String newUrl = url+"starships/"+cmd.getOptionValue("id");
                return new DeleteStarshipCommand(newUrl);
            }
            case "delete-movie":{
                cmd = parseCommandLine(DeleteMovieCommand.getDeleteOptions(),commandOptions);
                String newUrl = url+"movies/"+cmd.getOptionValue("id");
                return new DeleteMovieCommand(newUrl);
            }
            default: throw new InvalidInputException("No such command: " + mainCommand);
        }
    }

    private CommandLine parseCommandLine(Options options, String[] commandArguments) throws ParseException{
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options,commandArguments);
    }

}
