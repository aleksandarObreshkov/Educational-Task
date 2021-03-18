package com.example.app.printing;

import java.util.ArrayList;
import java.util.List;

import model.Movie;

public class MoviePrinter {

    public void printMovieTable(List<Movie> movies) {
        String[][] movieTable = createMovieTableArray(movies);
        TablePrinter.printDataTable(movieTable);
    }

    private String[][] createMovieTableArray(List<Movie> movies) {
        String[][] movieTable = new String[movies.size() + 1][4];
        movieTable[0][0] = "Id";
        movieTable[0][1] = "Title";
        movieTable[0][2] = "Rating";
        movieTable[0][3] = "Release Date";

        for (int i = 1; i < movies.size() + 1; i++) {
            movieTable[i][0] = movies.get(i - 1).getId().toString();
            movieTable[i][1] = movies.get(i - 1).getTitle();
            movieTable[i][2] = movies.get(i - 1).getRating().toString();
            movieTable[i][3] = movies.get(i - 1).getReleaseDate().toString();
        }

        return movieTable;
    }

    // TODO You can do even better than what you've done above. Working with arrays can be very error prone, especially
    // when you start hardcoding indexes like you have. Consider doing something like the following:

    private Table createMovieTableArray2(List<Movie> movies) {
        MovieRepresenter representer = new MovieRepresenter();
        Table table = new Table(representer.getColumnNames());
        for (Movie movie : movies) {
            table.rows.add(representer.getValues(movie));
        }
        return table;
    }

    private static class Table {

        private final List<String> headerRow;
        private List<List<String>> rows = new ArrayList<>();

        public Table(List<String> headerRow) {
            this.headerRow = headerRow;
        }

    }

    private static class MovieRepresenter {

        public List<String> getColumnNames() {
            return List.of("ID", "Title", "Rating", "Release Date");
        }

        public List<String> getValues(Movie movie) {
            List<String> values = new ArrayList<>();
            values.add(movie.getId().toString());
            values.add(movie.getTitle());
            values.add(movie.getRating().toString());
            values.add(movie.getReleaseDate().toString());
            return values;
        }

    }

}
