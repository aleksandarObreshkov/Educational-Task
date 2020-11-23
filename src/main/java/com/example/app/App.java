package com.example.app;
import com.example.app.domain.Movie;
import com.example.app.domain.Character;
import com.example.app.domain.Starship;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.*;
import sun.awt.geom.AreaOp;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.*;

public class App 
{
    public static Options optionsSetup(String mainOption){
        Options options = new Options();

        switch (mainOption){
            case "add-movie":{
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
                        .type(Date.class)
                        .build();
                options.addOption(title);
                options.addOption(rating);
                options.addOption(releaseDate);

                return options;
            }
            case "add-character":{
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
            case "add-starship":{
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
        }

        if (mainOption.contains("delete")){
            options.addOption("id", true, "delete an item with the specified id");
            return options;
        }
        else {
            System.out.printf("Non-existing command \"%s\" ", mainOption);
            return options;
        }
    }

    public static void writeDataToFile(List data, File file) throws IOException{
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(file, data);
    }

    public static void main(String[] args) {

        args = new String[]{"add-movie", "-t", "Hello", "-r", "r","-d", "2000-12-12"};


        //just file names
        File fileCharacters = new File("C:/Users/I542072/app/src/main/resources/dummyCharacter.json");
        File fileMovies= new File("C:/Users/I542072/app/src/main/resources/dummyMovies.json");
        File fileStarships = new File("C:/Users/I542072/app/src/main/resources/dummyStarships.json");

        //storage for the data
        List<Character> characters=new ArrayList<>();
        List<Movie> movies=new ArrayList<>();
        List<Starship> ships=new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();

        //list init
        try {
            //separate try-catch ?
            characters.addAll(Arrays.asList(mapper.readValue(fileCharacters, Character[].class)));
            movies.addAll(Arrays.asList(mapper.readValue(fileMovies, Movie[].class)));
            ships.addAll(Arrays.asList(mapper.readValue(fileStarships,Starship[].class)));

        } catch (IOException e) {
            e.printStackTrace();
        }

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        if(args[0].equals("movies")&&!movies.isEmpty()) {
            for (Movie a:movies) {
                System.out.println(a.getTitle());
            }
        }
        else if(args[0].equals("characters")&&!characters.isEmpty()) {
            for (Character a:characters) {
                System.out.println(a.getName());
            }
        }
        else if(args[0].equals("starships")&&!ships.isEmpty()) {
            for (Starship a:ships) {
                System.out.println(a.getName());
            }
        }
        else{
            Options options = optionsSetup(args[0]);

            try {
                if (options.getOptions().isEmpty())throw new ParseException("Invalid command name");
                String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
                cmd = parser.parse(options, newArgs);

                switch (args[0]) {
                    case "add-movie":{
                        try {
                            Movie newMovie = new Movie(
                                    UUID.randomUUID().toString(),
                                    cmd.getOptionValue("t"),
                                    Date.valueOf(cmd.getOptionValue("d")),
                                    Float.parseFloat(cmd.getOptionValue("r")));
                            movies.add(newMovie);
                            writeDataToFile(movies, fileMovies);

                        }catch (NumberFormatException e){
                            System.out.println("Rating should be float.");
                        }catch (IllegalArgumentException e){
                            System.out.println("Wrong date format.");
                        }
                        break;
                    }
                    case "add-character":{

                        try{
                            Character newCharacter = new Character(
                                    UUID.randomUUID().toString(),
                                    cmd.getOptionValue("name"),
                                    Integer.parseInt(cmd.getOptionValue("age")),
                                    false);
                            if (cmd.hasOption("force-user")) newCharacter.setForceUser(true);
                            characters.add(newCharacter);

                            writeDataToFile(characters, fileCharacters);
                            break;

                        }catch (NumberFormatException e){
                            System.out.println("Age should be an integer.");
                            }
                        }
                    case "add-starship": {

                        try {
                            Starship newShip = new Starship(
                                    UUID.randomUUID().toString(),
                                    cmd.getOptionValue("n"),
                                    Float.parseFloat(cmd.getOptionValue("l"))
                            );
                            ships.add(newShip);

                            writeDataToFile(ships, fileStarships);
                            break;
                        }catch (NumberFormatException e){
                            System.out.println("Length should be float.");
                        }

                    }

                    //too brute force, optimization possible
                    case "delete-movie": {
                        try{
                            String idToDelete = cmd.getOptionValue("id");
                            int id = 0;
                            for (Movie a : movies) {
                                if (a.getId().equals(idToDelete)) {
                                    id = movies.indexOf(a);
                                    movies.remove(id);
                                    break;
                                }
                            }
                            writeDataToFile(movies, fileMovies);
                            break;
                        }catch (NumberFormatException e){
                            System.out.println("Id should be an integer.");
                        }

                    }
                    case "delete-character": {

                        try {
                            String idToDelete = cmd.getOptionValue("id");
                            int id = 0;
                            for (Character a : characters) {
                                if (a.getId().equals(idToDelete)) {
                                    id = characters.indexOf(a);
                                    characters.remove(id);
                                    break;
                                }
                            }


                            writeDataToFile(characters, fileCharacters);
                            break;
                        }catch (NumberFormatException e){
                            System.out.println("Id should be an integer");
                        }


                    }
                    case "delete-starship": {
                        try {
                            String idToDelete = cmd.getOptionValue("id");
                            int id = 0;
                            for (Starship a : ships) {
                                if (a.getId().equals(idToDelete)) {
                                    id = ships.indexOf(a);
                                    ships.remove(id);
                                }
                            }
                            writeDataToFile(ships, fileStarships);
                            break;
                        }catch (NumberFormatException e){
                            System.out.println("Id should be an integer");
                        }
                    }
                    default:{
                        System.out.println("Non-existing command.");
                    }
                }
            //missing required option
            }catch (MissingOptionException e){
                //see if its possible to get the long names of the options
                System.out.println(e.getMessage());
            }
            //catches an unrecognised option
            catch (UnrecognizedOptionException e){
                System.out.println("Non-existing option "+e.getOption());
            }
            //default parser handler (catches UnrecognisedException if main command doesnt exist)
            catch (ParseException e) {
                //e.printStackTrace();
            }catch (IOException ioe){
                System.out.println("Error when writing data to file.");
            }
        }
    }
}
