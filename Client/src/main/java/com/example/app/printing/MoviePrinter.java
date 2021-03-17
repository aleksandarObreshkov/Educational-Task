package com.example.app.printing;

import model.Movie;

import java.util.List;

public class MoviePrinter {

    public void printMovieTable(List<Movie> movies){
        String[][] movieTable = createMovieTableArray(movies);
        TablePrinter.printDataTable(movieTable);
    }

    private String[][] createMovieTableArray(List<Movie> movies){
        String[][] movieTable = new String[movies.size()+1][4];
        movieTable[0][0] = "Id";
        movieTable[0][1] = "Title";
        movieTable[0][2] = "Rating";
        movieTable[0][3] = "Release Date";

        for (int i = 1; i< movies.size()+1; i++){
            movieTable[i][0] = movies.get(i-1).getId().toString();
            movieTable[i][1] = movies.get(i-1).getTitle();
            movieTable[i][2] = movies.get(i-1).getRating().toString();
            movieTable[i][3] = movies.get(i-1).getReleaseDate().toString();
        }

        return movieTable;
    }

}
