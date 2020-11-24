package com.example.app;
import com.example.app.domain.Movie;
import com.example.app.domain.Character;
import com.example.app.domain.Starship;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;

import java.time.format.DateTimeParseException;
import java.util.*;

public class App 
{
    public static void writeDataToFile(Object data, File file) throws IOException{
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(file, data);
    }
    public static void printData(List data){
        for(Object a : data){
            System.out.println(a.toString());
        }
    }

    public static void main(String[] args) {

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

        switch (args[0]){
            case "movies":{
                printData(movies);
                break;
            }
            case "starships":{
                printData(ships);
                break;
            }
            case "characters":{
                printData(characters);
                break;
            }
            default: {
                try {
                    Options options = CliOptions.optionsSetup(args[0]);
                    String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
                    cmd = parser.parse(options, newArgs);

                    switch (args[0]) {
                        case "add-movie":{
                            try {
                                movies.add(CmdCommands.createMovie(cmd));
                                writeDataToFile(movies, fileMovies);
                            }catch (NumberFormatException e){
                                System.out.println("Rating should be float.");
                            }catch (DateTimeParseException e){
                                System.out.println("Wrong date format.");
                            }
                            break;
                        }
                        case "add-character":{
                            try{
                                characters.add(CmdCommands.createCharacter(cmd));
                                writeDataToFile(characters, fileCharacters);
                                break;
                            }catch (NumberFormatException e){
                                System.out.println("Age should be an integer.");
                            }
                        }
                        case "add-starship": {
                            try {
                                ships.add(CmdCommands.createStarship(cmd));
                                writeDataToFile(ships, fileStarships);
                                break;
                            }catch (NumberFormatException e){
                                System.out.println("Length should be float.");
                            }
                        }
                        case "delete-movie": {
                            try{
                                String id = cmd.getOptionValue("id");
                                CmdCommands.deleteMovie(id, movies);
                                writeDataToFile(movies, fileMovies);
                                break;
                            }catch (NumberFormatException e){
                                System.out.println("Id should be an integer.");
                            }

                        }
                        case "delete-character": {

                            try {
                                String id = cmd.getOptionValue("id");
                                CmdCommands.deleteCharacter(id, characters);
                                writeDataToFile(characters, fileCharacters);
                                break;
                            }catch (NumberFormatException e){
                                System.out.println("Id should be an integer");
                            }


                        }
                        case "delete-starship": {
                            try {
                                String id = cmd.getOptionValue("id");
                                CmdCommands.deleteStarship(id, ships);
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
                }
                //catches an unrecognised option
                catch (UnrecognizedOptionException e){
                    System.out.println("Non-existing option "+e.getOption());
                }
                //catches both "no such option" and "no such command errors"
                catch (ParseException e){
                    System.out.println(e.getMessage());
                }
                //catches errors when writing to the files
                catch (IOException ioe){
                    System.out.println("Error when writing data to file.");
                }
            }
        }


    }
}

