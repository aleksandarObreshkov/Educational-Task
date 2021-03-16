package com.example.app.utils;

import model.*;
import model.Character;
import java.util.*;
import java.util.stream.Collectors;

public class DataPrintingUtil {

    public static <T> void printList(List<T> data){
        Class<?> entityClass = data.get(0).getClass();
        if (Character.class.isAssignableFrom(entityClass)){
            List<Character> characters = (List<Character>) data;
            printCharactersTable(characters);
            printFriendsTable(characters);
            printCharacterStarshipsTable(characters);
        }
        else if (entityClass.equals(Movie.class)){
            List<Movie> movies = (List<Movie>)data;
            printMovieTable(movies);
        }
        else if(entityClass.equals(Starship.class)){
            List<Starship> starships = (List<Starship>)data;
            printStarshipsTable(starships);
        }
    }

    private static void printCharactersTable(List<Character> characters) {
        String[][] characterTable = createCharacterTableArray(characters);
        printDataTable(characterTable);
    }
    private static void printFriendsTable(List<Character> characters){
        String[][] friendsTable = createFriendsTable(characters);
        printDataTable(friendsTable);
    }
    private static void printCharacterStarshipsTable(List<Character> characters){
        String[][] characterStarshipTable = createCharacterStarshipTable(characters);
        printDataTable(characterStarshipTable);
    }
    private static void printMovieTable(List<Movie> movies){
        String[][] movieTable = createMovieTableArray(movies);
        printDataTable(movieTable);

    }
    private static void printStarshipsTable(List<Starship> starships){
        String[][] starshipTable = createStarshipTableArray(starships);
        printDataTable(starshipTable);
    }

    private static void printDataTable(String[][] data){
        Map<Integer, Integer> columnLengths = setupColumnLengths(data);
        String formatString = prepareFormatString(columnLengths);
        String line = prepareLine(columnLengths);
        printTable(formatString, line, data);
    }

    private static String[][] createFriendsTable(List<Character> characters){
        String[][] friendsTable = new String[characters.size()+1][2];
        friendsTable[0][0] = "Character's Name";
        friendsTable[0][1] = "Friend's Name";

        for (int i = 1; i< characters.size()+1; i++){
            friendsTable[i][0] = characters.get(i-1).getName();
            friendsTable[i][1] = createStringFromList(createNameListFromCharacters(characters.get(i-1).getFriends()));
        }
        return friendsTable;
    }
    private static String[][] createCharacterStarshipTable(List<Character> characters){
        List<Human> allHumans = getAllHumans(characters);
        String[][] starshipsTable = new String[allHumans.size()+1][2];
        starshipsTable[0][0] = "Character's Name";
        starshipsTable[0][1] = "Starship's Name";

        for (int i = 1; i< allHumans.size()+1; i++){
            starshipsTable[i][0] = allHumans.get(i-1).getName();
            starshipsTable[i][1] = createStringFromList(createNameListFromStarships(allHumans.get(i-1).getStarships()));
        }
        return starshipsTable;
    }
    private static String[][] createMovieTableArray(List<Movie> movies){
        String[][] movieTable = new String[movies.size()+1][3];
        movieTable[0][0] = "Title";
        movieTable[0][1] = "Rating";
        movieTable[0][2] = "Release Date";

        for (int i = 1; i< movies.size()+1; i++){
            movieTable[i][0] = movies.get(i-1).getTitle();
            movieTable[i][1] = movies.get(i-1).getRating().toString();
            movieTable[i][2] = movies.get(i-1).getReleaseDate().toString();
        }

        return movieTable;
    }
    private static String[][] createStarshipTableArray(List<Starship> starships){
        String[][] starshipTable = new String[starships.size()+1][2];
        starshipTable[0][0] = "Name";
        starshipTable[0][1] = "Length";

        for (int i = 1; i< starships.size()+1; i++){
            starshipTable[i][0] = starships.get(i-1).getName();
            starshipTable[i][1] = starships.get(i-1).getLength().toString();
        }
        return starshipTable;
    }
    private static String[][] createCharacterTableArray(List<Character> characters){
        String[][] characterTable = new String[characters.size()+1][5];
        characterTable[0][0] = "Name";
        characterTable[0][1] = "Age";
        characterTable[0][2] = "Force User";
        characterTable[0][3] = "Character Type";
        characterTable[0][4] = "Primary function";

        for (int i = 1; i< characters.size()+1; i++){
            characterTable[i][0] = characters.get(i-1).getName();
            characterTable[i][1] = characters.get(i-1).getAge().toString();
            characterTable[i][2] = characters.get(i-1).isForceUser()+"";
            characterTable[i][3] = characters.get(i-1).getCharacterType();
            characterTable[i][4] = "";
            if (characters.get(i-1).getClass().equals(Droid.class)){
                Droid tempDroid = (Droid)characters.get(i-1);
                characterTable[i][4] = tempDroid.getPrimaryFunction();
            }
        }
        return characterTable;
    }
    
    private static Map<Integer, Integer> setupColumnLengths(String[][] array){
        Map<Integer, Integer> columnLengths = new HashMap<>();
        for (String[] row : array){
            for (int i = 0; i< row.length; i++){
                columnLengths.putIfAbsent(i, 0);
                if (columnLengths.get(i) < row[i].length()) {
                    columnLengths.put(i, row[i].length());
                }
            }
        }
        return columnLengths;
    }
    private static String prepareFormatString(Map<Integer, Integer> columnLengths){
        final StringBuilder formatString = new StringBuilder("");
        columnLengths.forEach((key, value) -> formatString.append("| %").append("-").append(value).append("s "));
        formatString.append("|\n");
        return formatString.toString();
    }
    private static String prepareLine(Map<Integer, Integer> columnLengths){
        StringBuilder result = new StringBuilder();
        for (Integer columnSize : columnLengths.values()){
            result.append("+-");
            result.append("-".repeat(columnSize+1));
        }
        result.append("+\n");
        return result.toString();
    }
    private static void printTable(String formatString, String line, String[][] data){
        System.out.print(line);
        System.out.printf(formatString, data[0]);
        System.out.print(line);
        for (int i = 1; i< data.length; i++){
            System.out.printf(formatString, data[i]);
        }
        System.out.println(line);
        System.out.println();
    }

    private static List<String> createNameListFromCharacters(List<Character> characters){
        List<String> result = new ArrayList<>();
        for (Character character : characters){
            result.add(character.getName());
        }
        return result;
    }
    private static List<String> createNameListFromStarships(List<Starship> starships){
        List<String> result = new ArrayList<>();
        for (Starship starship : starships){
            result.add(starship.getName());
        }
        return result;
    }
    private static List<Human> getAllHumans(List<Character> characters){
        return characters.stream()
                .filter(character -> character.getCharacterType().equals("human"))
                .map(character -> (Human)character)
                .collect(Collectors.toList());
    }

    private static String createStringFromList(List<String> dataList){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i< dataList.size(); i++){
            builder.append(dataList.get(i));
            if (i != dataList.size()-1){
                builder.append(", ");
            }
        }
        return builder.toString();
    }
}
