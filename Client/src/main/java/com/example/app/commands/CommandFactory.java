package com.example.app.commands;

import com.example.app.commands.addCommands.AddCharacterCommand;
import com.example.app.commands.addCommands.AddMovieCommand;
import com.example.app.commands.addCommands.AddStarshipCommand;
import com.example.app.commands.deleteCommands.DeleteCharacterCommand;
import com.example.app.commands.deleteCommands.DeleteMovieCommand;
import com.example.app.commands.deleteCommands.DeleteStarshipCommand;
import com.example.app.commands.showCommands.ShowCharactersCommand;
import com.example.app.commands.showCommands.ShowMoviesCommand;
import com.example.app.commands.showCommands.ShowStarshipsCommand;
import com.example.app.errors.InvalidInputException;
import org.apache.commons.cli.*;
import java.util.Arrays;

public class CommandFactory {

    public Command createCommand(String[] arguments) throws ParseException {

        final String url = "http://localhost:8080/";
        String mainCommand = arguments[0];
        String[] commandOptions = Arrays.copyOfRange(arguments, 1, arguments.length);
        switch (mainCommand) {

            case "characters": return new ShowCharactersCommand(url+mainCommand);
            case "movies": return new ShowMoviesCommand(url+mainCommand);
            case "starships":  return new ShowStarshipsCommand(url+mainCommand);
            case "add-movie": {
                CommandLine cmd = parseCommandLine(AddMovieCommand.getAddMovieOptions(),commandOptions);
                return new AddMovieCommand(cmd, url+"movies");
            }
            case "add-character": {
                CommandLine cmd = parseCommandLine(AddCharacterCommand.getAddCharacterOptions(),commandOptions);
                return new AddCharacterCommand(cmd, url+"characters");
            }
            case "add-starship": {
                CommandLine cmd = parseCommandLine(AddStarshipCommand.getAddStarshipOptions(),commandOptions);
                return  new AddStarshipCommand(cmd, url+"starships");
            }
            case "delete-character":{
                CommandLine cmd = parseCommandLine(DeleteCharacterCommand.getDeleteOptions(),commandOptions);
                String newUrl = url+"characters/"+cmd.getOptionValue("id");
                return new DeleteCharacterCommand(newUrl);
            }
            case "delete-starship": {
                CommandLine cmd = parseCommandLine(DeleteStarshipCommand.getDeleteOptions(),commandOptions);
                String newUrl = url+"starships/"+cmd.getOptionValue("id");
                return new DeleteStarshipCommand(newUrl);
            }
            case "delete-movie":{
                CommandLine cmd = parseCommandLine(DeleteMovieCommand.getDeleteOptions(),commandOptions);
                String newUrl = url+"movies/"+cmd.getOptionValue("id");
                return new DeleteMovieCommand(newUrl);
            }
            case "list":{
                return new ListCommand();
            }
            default: throw new InvalidInputException("No such command: " + mainCommand, new IllegalArgumentException());
        }
    }

    private CommandLine parseCommandLine(Options options, String[] commandArguments) throws ParseException{
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options,commandArguments);
    }

}
