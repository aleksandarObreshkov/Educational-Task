package com.example.app;

import com.example.app.commands.*;
import org.apache.commons.cli.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

public class CliOptions {

    private static Options options=new Options();

    public static Command optionsSetup(String[] arguments)throws ParseException, IOException {

        CommandLineParser parser = new DefaultParser();
        String[] newArgs = Arrays.copyOfRange(arguments, 1, arguments.length);
        CommandLine cmd;
/*
        switch (mainOption){
            case "add-movie":{
                return getAddMovieOptions();
            }
            case "add-character":{
                return getAddCharacterOptions();
            }
            case "add-starship":{
                return getAddStarshipOptions();
            }
            case "movies":
            case "starships":
            case "characters":{
                return new Options();
            }
        }
        if (mainOption.equals("delete-movies")||
            mainOption.equals("delete-character")||
            mainOption.equals("delete-starship")){
            return getDeleteOptions();
        }
        else {
            throw new ParseException("Non-existing command \""+mainOption+"\"");
        }
 */
        switch (arguments[0]) {
            case "characters":
            case "movies":
            case "starships": return new ShowDataCommand(arguments[0]);
            case "add-movie": {
                options = getAddMovieOptions();
                cmd = parser.parse(options, newArgs);
                return new AddMovieCommand(cmd);
            }
            case "add-character": {
                options = getAddCharacterOptions();
                cmd = parser.parse(options, newArgs);
                return new AddCharacterCommand(cmd);
            }
            case "add-starship": {
                options = getAddStarshipOptions();
                cmd = parser.parse(options, newArgs);
                return  new AddStarshipCommand(cmd);
            }
            case "delete-character": {
                options = getDeleteOptions();
                cmd = parser.parse(options, newArgs);
                return new DeleteCharacterCommand(cmd.getOptionValue("id"));
            }
            case "delete-starship": {
                options = getDeleteOptions();
                cmd = parser.parse(options, newArgs);
                return new DeleteStarshipCommand(cmd.getOptionValue("id"));
            }
            case "delete-movie":{
                options = getDeleteOptions();
                cmd = parser.parse(options, newArgs);
                return new DeleteMovieCommand(cmd.getOptionValue("id"));
            }
            default: throw new IOException("No such command: " + arguments[0]);
        }
    }

    private static Options getAddMovieOptions(){
        Option title = Option.builder("t")
                .longOpt("title")
                .hasArg()
                .required()
                .type(String.class)
                .build();
        Option rating = Option.builder("r")
                .longOpt("rating")
                .hasArg()
                .required()
                .type(Float.class)
                .build();

        Option releaseDate = Option.builder("d")
                .longOpt("release-date")
                .hasArg()
                .required()
                .type(LocalDate.class)
                .build();

        options.addOption(title);
        options.addOption(rating);
        options.addOption(releaseDate);

        return options;
    }

    private static Options getAddCharacterOptions(){

        Option name = Option.builder("n")
                .longOpt("name")
                .hasArg()
                .required()
                .type(String.class)
                .build();
        Option age = Option.builder("a")
                .longOpt("age")
                .hasArg()
                .required()
                .type(Integer.class)
                .build();

        Option forceUser = Option.builder("f")
                .longOpt("force-user")
                .build();

        options.addOption(name);
        options.addOption(age);
        options.addOption(forceUser);
        return options;
    }

    private static Options getAddStarshipOptions(){
        Option name = Option.builder("n")
                .longOpt("name")
                .hasArg()
                .required()
                .type(String.class)
                .build();
        Option length = Option.builder("l")
                .longOpt("length")
                .hasArg()
                .required()
                .type(Float.class)
                .build();

        options.addOption(name);
        options.addOption(length);
        return options;
    }

    private static Options getDeleteOptions(){

        options.addOption("id", true, "delete an item with the specified id");
        return options;
    }
}
