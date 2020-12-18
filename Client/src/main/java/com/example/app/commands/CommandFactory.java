package com.example.app.commands;

import org.apache.commons.cli.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

public class CommandFactory {

    private Options options=new Options(); // TODO: Reduce the scope of this variable.
    private String url = "http://localhost:8080/"; // TODO: Turn this into a constant - see my comment on line 31.

    public CommandFactory() { // TODO: Unnecessary constructor.
    }

    public Command commandSetup(String[] arguments) throws ParseException, IOException, NullPointerException {

        CommandLineParser parser = new DefaultParser();
        // TODO: Give a better name to this variable. Something like "commandOptions" or just "options" comes to mind:
        String[] newArgs = Arrays.copyOfRange(arguments, 1, arguments.length);
        CommandLine cmd; // TODO: Reduce the scope of this variable.

        switch (arguments[0]) {
            // TODO: There's no need for the curly brackets on the next line, as you have only one statement inside. And yes, I know this conflicts with what I said
            // about if statements in a different class. Different rules about different constructs. :D
            case "characters":{ return new ShowCharactersCommand(url+arguments[0]);}  
            case "movies":{ return new ShowMoviesCommand(url+arguments[0]);}
            case "starships": { return new ShowStarshipsCommand(url+arguments[0]);}
            case "add-movie": {
                options = getAddMovieOptions();
                cmd = parser.parse(options, newArgs);
                url +="movies";  // TODO: If I call commandSetup twice with "add-movie" and "add-character", then the URL for the second invocation would be
                                 // incorrect (http://localhost:8080/moviescharacters). In general you should avoid having modifiable state in a class if possible,
                                 // because that gives you things like thread-safety and call flexibility out-of-the-box.
                return new AddMovieCommand(cmd, url);
            }
            case "add-character": {
                options = getAddCharacterOptions();
                cmd = parser.parse(options, newArgs); // TODO: This is repeated in a lot of places. Extract it in a separate method.
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
            // TODO: This is not a good use of an IOException. They should only be thrown for I/O related errors like reading or writing to a file,
            // network communication, etc. Furthermore, unchecked exceptions are better in most cases:
            // http://www.douevencode.com/articles/2017-10/checked-vs-unchecked-exceptions/
            default: throw new IOException("No such command: " + arguments[0]);
        }
    }

    // TODO: Consider what will happen if you were to add 5-6 different options to each command and 10 new commands. This class would become enormous.
    // Move these options to their respective commands somehow to avoid this problem.
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
