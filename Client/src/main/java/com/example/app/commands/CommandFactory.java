package com.example.app.commands;

import org.apache.commons.cli.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

public class CommandFactory {

    private Options options=new Options();
    private String url = "http://localhost:8080/";

    public CommandFactory() {
    }

    public Command commandSetup(String[] arguments) throws ParseException, IOException, NullPointerException {

        CommandLineParser parser = new DefaultParser();
        String[] newArgs = Arrays.copyOfRange(arguments, 1, arguments.length);
        CommandLine cmd;

        switch (arguments[0]) {
            case "characters":{ return new ShowCharactersCommand(url+arguments[0]);}
            case "movies":{ return new ShowMoviesCommand(url+arguments[0]);}
            case "starships": { return new ShowStarshipsCommand(url+arguments[0]);}
            case "add-movie": {
                options = getAddMovieOptions();
                cmd = parser.parse(options, newArgs);
                url +="movies";
                return new AddMovieCommand(cmd, url);
            }
            case "add-character": {
                options = getAddCharacterOptions();
                cmd = parser.parse(options, newArgs);
                url += "characters";
                return new AddCharacterCommand(cmd, url);
            }
            case "add-starship": {
                options = getAddStarshipOptions();
                cmd = parser.parse(options, newArgs);
                url += "starships";
                return  new AddStarshipCommand(cmd, url);
            }
            case "delete-character":{
                options=getDeleteOptions();
                cmd = parser.parse(options,newArgs);
                url = url+"characters/"+cmd.getOptionValue("id");
                return new DeleteCharacterCommand(url);
            }
            case "delete-starship": {
                options=getDeleteOptions();
                cmd = parser.parse(options,newArgs);
                url = url+"starships/"+cmd.getOptionValue("id");
                return new DeleteStarshipCommand(url);
            }
            case "delete-movie":{
                options = getDeleteOptions();
                cmd = parser.parse(options, newArgs);
                url = url+"movies/"+cmd.getOptionValue("id");
                return new DeleteMovieCommand(url);
            }
            default: throw new IOException("No such command: " + arguments[0]);
        }
    }

    private  Options getAddMovieOptions(){
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

    private  Options getAddCharacterOptions(){

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

        Option characterType = Option.builder("t")
                .longOpt("type")
                .required()
                .hasArg()
                .type(String.class)
                .build();
        Option primaryFunction = Option.builder("pf")
                .longOpt("primaryFunction")
                .hasArg()
                .type(String.class)
                .build();

        options.addOption(name);
        options.addOption(age);
        options.addOption(forceUser);
        options.addOption(characterType);
        options.addOption(primaryFunction);
        return options;
    }

    private  Options getAddStarshipOptions(){
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

    private  Options getDeleteOptions(){

        options.addOption("id", true, "delete an item with the specified id");
        return options;
    }
}
