package com.example.app.commands;

import com.example.app.CliOptions;
import com.example.app.FileCreator;
import com.example.app.commands.*;
import com.example.app.domain.Movie;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import java.util.Arrays;
import java.util.List;

public class CommandFactory {

    public static Command getCommand(String[] arguments) throws Exception {
        //cmd setup
        CommandLineParser parser = new DefaultParser();
        Options options = CliOptions.optionsSetup(arguments[0]);
        String[] newArgs = Arrays.copyOfRange(arguments, 1, arguments.length);
        CommandLine cmd = parser.parse(options, newArgs);

        Command returnCommand;
        String input = arguments[0];

        switch (input) {

            case "characters":
            case "movies":
            case "starships":
                returnCommand = new ShowDataCommand(input);
                break;

            case "add-movie": {
                returnCommand = new AddMovieCommand(cmd);
                break;
            }

            case "add-character": {
                returnCommand = new AddCharacterCommand(cmd);
                break;
            }
            case "add-starship": {
                returnCommand = new AddStarshipCommand(cmd);
                break;
            }

            case "delete-character":
            case "delete-starship":
            case "delete-movie": {
                returnCommand = new DeleteCommand(arguments[0], arguments[2]);
                break;
            }
            default: {
                throw new Exception("No such command: " + input);
            }

        }
        return returnCommand;
    }
}



