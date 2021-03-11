package com.example.app.utils;

import model.Character;
import model.Movie;
import model.Starship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataPrintingUtil {

    public static <T> void printList(List<T> data){
        Class<?> entityClass = data.get(0).getClass();
        if (Arrays.asList(entityClass.getInterfaces()).contains(Character.class)){
            List<Character> characters = new ArrayList<>();
            for (Object item : data){
                characters.add((Character) item);
            }
            printCharactersTable(characters);
        }
        else if (entityClass.equals(Movie.class)){
            List<Movie> movies = new ArrayList<>();
            for (Object item : data){
                movies.add((Movie) item);
            }
            printMovieTable(movies);
        }
        else if(entityClass.equals(Starship.class)){
            List<Starship> starships = new ArrayList<>();
            for (Object item : data){
                starships.add((Starship) item);
            }
            printStarshipsTable(starships);
        }
    }

    private static void printMovieTable(List<Movie> movies){
        int longestTitle = "Title".length();
        int longestRating = "Rating".length();
        int longestDate = "Release Date".length();
        for (Movie movie : movies){
            if (movie.getTitle().length()>longestTitle){
                longestTitle=movie.getTitle().length();
            }
            if (movie.getRating().toString().length()>longestRating){
                longestRating=movie.getRating().toString().length();
            }
            if (movie.getReleaseDate().toString().length()>longestDate){
                longestDate=movie.getReleaseDate().toString().length();
            }
        }
        System.out.printf("|Title%s|Rating%s|ReleaseDate%s|\n",
                createSpaces(longestTitle-"Title".length()+1),
                createSpaces(longestRating-"rating".length()+1),
                createSpaces(longestDate-"release date".length()+2));
        for (Movie movie : movies){
            System.out.printf("|%s%s|%s%s|%s%s|\n",
                    movie.getTitle(), createSpaces(longestTitle-movie.getTitle().length()+1),
                    movie.getRating(), createSpaces(longestRating-movie.getRating().toString().length()+1),
                    movie.getReleaseDate(), createSpaces(longestDate-movie.getReleaseDate().toString().length()+1));

        }
    }
    private static void printCharactersTable(List<Character> characters){
        int longestName = "Name".length();
        int longestAge = "Age".length();
        int longestCharacterType = "Character Type".length();
        for (Character character : characters){
            if (character.getName().length()>longestName){
                longestName=character.getName().length();
            }
            if (character.getAge().toString().length()>longestAge){
                longestAge=character.getAge().toString().length();
            }
            if (character.getCharacterType().length()>longestCharacterType){
                longestCharacterType=character.getCharacterType().length();
            }
        }
        System.out.printf("|Name%s|Age%s|Character Type%s|\n",
                createSpaces(longestName-"Name".length()+1),
                createSpaces(longestAge-"Age".length()+1),
                createSpaces(longestCharacterType-"Character type".length()+2));
        for (Character character : characters){
            System.out.printf("|%s%s|%s%s|%s%s|\n",
                    character.getName(), createSpaces(longestName- character.getName().length()+1),
                    character.getAge(), createSpaces(longestAge- character.getAge().toString().length()+1),
                    character.getCharacterType(), createSpaces(longestCharacterType- character.getCharacterType().length()+1));
        }
    }
    private static void printStarshipsTable(List<Starship> starships){
        int longestName = "Name".length();
        int longestLength = "Length".length();
        for (Starship starship : starships){
            if (starship.getName().length()>longestName){
                longestName=starship.getName().length();
            }
            if (starship.getLength().toString().length()>longestLength){
                longestLength=starship.getLength().toString().length();
            }
        }
        System.out.printf("|Name%s|Length%s|\n",
                createSpaces(longestName-"Name".length()+1),
                createSpaces(longestLength-"Length".length()+1));
        for (Starship starship : starships){
            System.out.printf("|%s%s|%s%s|\n",
                    starship.getName(), createSpaces(longestName-starship.getName().length()+1),
                    starship.getLength(), createSpaces(longestLength-starship.getLength().toString().length()+1));
        }
    }
    private static String createSpaces(int spaces){
        return " ".repeat(Math.max(0, spaces));
    }

}
