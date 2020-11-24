package com.example.app;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.time.LocalDate;

public class CliOptions {

    private static Options options=new Options();

    public static Options optionsSetup(String mainOption)throws ParseException {
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
        }
        if (mainOption.contains("delete")){
            return getDeleteOptions();
        }
        else {
            throw new ParseException("Non-existing command \""+mainOption+"\"");
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

    public static Options getAddCharacterOptions(){

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

    public static Options getAddStarshipOptions(){
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

    public static Options getDeleteOptions(){

        options.addOption("id", true, "delete an item with the specified id");
        return options;
    }
}
