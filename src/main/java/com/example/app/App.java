package com.example.app;
import com.example.app.domain.Movie;
import com.example.app.domain.Character;
import com.example.app.domain.Starship;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.UUID;

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
                        .build();
                Option rating = Option.builder("r")
                        .longOpt("rating")
                        .hasArg()
                        .required()
                        .build();

                Option releaseDate = Option.builder("d")
                        .longOpt("release-date")
                        .hasArg()
                        .required()
                        .build();

                options.addOption(title);
                options.addOption(rating);
                options.addOption(releaseDate);

                break;
            }

            case "add-character":{
                Option name = Option.builder("n")
                        .longOpt("name")
                        .hasArg()
                        .required()
                        .build();
                Option age = Option.builder("a")
                        .longOpt("age")
                        .hasArg()
                        .required()
                        .build();

                Option forceUser = Option.builder("f")
                        .longOpt("force-user")
                        .build();

                options.addOption(name);
                options.addOption(age);
                options.addOption(forceUser);
                break;
            }

            case "add-starship":{
                Option name = Option.builder("n")
                        .longOpt("name")
                        .hasArg()
                        .required()
                        .build();
                Option length = Option.builder("l")
                        .longOpt("length")
                        .hasArg()
                        .required()
                        .build();

                options.addOption(name);
                options.addOption(length);
                break;

            }

        }
        if (mainOption.contains("delete")){
            options.addOption("id", true, "delete an item with the specified id");
        }



        return options;
    }

    public static void writeDataToFile(ArrayList data, File file){
        ObjectMapper mapper=new ObjectMapper();
        try {
            mapper.writeValue(file, data);
        } catch (IOException e) {
            System.out.println("IO exception");
        }

    }

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();
        //just file names
        File fileCharacters = new File("C:/Users/I542072/app/src/main/resources/dummyCharacter.json");
        File fileMovies= new File("C:/Users/I542072/app/src/main/resources/dummyMovies.json");
        File fileStarships = new File("C:/Users/I542072/app/src/main/resources/dummyStarships.json");

        ArrayList<Character> characters=new ArrayList<>();
        ArrayList<Movie> movies=new ArrayList<>();
        ArrayList<Starship> ships=new ArrayList<>();

        //list init
        try {
            //separate try-catch
            Character[] a= mapper.readValue(fileCharacters, Character[].class);
            characters.addAll(Arrays.asList(a));
            movies.addAll(Arrays.asList(mapper.readValue(fileMovies, Movie[].class)));
            ships.addAll(Arrays.asList(mapper.readValue(fileStarships,Starship[].class)));

        } catch (IOException e) {
            e.printStackTrace();
        }

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd=null;

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
                cmd = parser.parse(options,args);

                if (args[0].equals("add-movie")){
                    Movie newMovie=new Movie(
                            UUID.randomUUID().toString(),
                            cmd.getOptionValue("t"),
                            Date.valueOf(cmd.getOptionValue("d")),
                            Float.parseFloat(cmd.getOptionValue("r")));
                    movies.add(newMovie);

                    writeDataToFile(movies,fileMovies);

                }
                else if(args[0].equals("add-character")){
                    Character newCharacter = new Character(
                            UUID.randomUUID().toString(),
                            cmd.getOptionValue("name"),
                            Integer.parseInt(cmd.getOptionValue("age")),
                            false
                    );
                    if (cmd.hasOption("force-user")) newCharacter.setForceUser(true);
                    characters.add(newCharacter);

                    writeDataToFile(characters, fileCharacters);
                }
                else if(args[0].equals("add-starship")){
                    Starship newShip= new Starship(
                            UUID.randomUUID().toString(),
                            cmd.getOptionValue("n"),
                            Float.parseFloat(cmd.getOptionValue("l"))
                    );
                    ships.add(newShip);

                    writeDataToFile(ships, fileStarships);
                }

                else if(args[0].equals("delete-movie")){
                    String idToDelete = cmd.getOptionValue("id");
                    int id = 0;
                    for(Movie a:movies){
                        if (a.getId().equals(idToDelete)){
                            id=movies.indexOf(a);
                            movies.remove(id);
                            break;
                        }
                    }
                    writeDataToFile(movies,fileMovies);
                }
                else if(args[0].equals("delete-character")){
                    String idToDelete = cmd.getOptionValue("id");
                    int id=0;
                    for(Character a:characters) {
                        if (a.getId().equals(idToDelete)) {
                            id = characters.indexOf(a);
                            characters.remove(id);
                            break;
                        }
                    }


                    writeDataToFile(characters, fileCharacters);
                }
                else if(args[0].equals("delete-starship")){
                    String idToDelete = cmd.getOptionValue("id");
                    int id=0;
                    for(Starship a:ships) {
                        if (a.getId().equals(idToDelete)) {
                            id = ships.indexOf(a);
                            ships.remove(id);
                        }
                    }
                    writeDataToFile(ships, fileStarships);
                }
            } catch (ParseException e) {
                e.getStackTrace();
            } catch (InputMismatchException e){
                System.out.println("Wrong input format");
            }


        }

    }
}
