package com.example.app.commands;

import com.example.app.CliOptions;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import java.util.Arrays;


public class CommandFactory {

    public static Command getCommand(String[] arguments) throws Exception {
        //cmd setup
        CommandLineParser parser = new DefaultParser();
        Options options = CliOptions.optionsSetup(arguments[0]);
        String[] newArgs = Arrays.copyOfRange(arguments, 1, arguments.length);
        CommandLine cmd = parser.parse(options, newArgs);

        String input = arguments[0];

        switch (input) {
            case "characters":
            case "movies":
            case "starships":
                return new ShowDataCommand(input);
            case "add-movie": return new AddMovieCommand(cmd);
            case "add-character": return new AddCharacterCommand(cmd);
            case "add-starship": return  new AddStarshipCommand(cmd);
            case "delete-character": return new DeleteCharacterCommand(arguments[2]);
            case "delete-starship": return new DeleteStarshipCommand(arguments[2]);
            case "delete-movie": return new DeleteMovieCommand(arguments[2]);
            default: throw new Exception("No such command: " + input);
        }
    }
}



