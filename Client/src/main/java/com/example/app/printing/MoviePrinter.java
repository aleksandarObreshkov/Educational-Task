package com.example.app.printing;

import java.util.ArrayList;
import java.util.List;

import com.example.model.Movie;

public class MoviePrinter {

    public void printMovieTable(List<Movie> movies) {
        Table movieTable = createMovieTableArray(movies);
        TablePrinter.printDataTable(movieTable);
    }

    private Table createMovieTableArray(List<Movie> movies) {
        MovieRepresenter representer = new MovieRepresenter();
        Table table = new Table(representer.getHeaderRow());
        for (Movie movie : movies) {
            table.getRows().add(representer.getRow(movie));
        }
        return table;
    }

    private static class MovieRepresenter {

        public List<String> getHeaderRow() {
            return List.of("ID", "Title", "Rating", "Release Date");
        }

        public List<String> getRow(Movie movie) {
            List<String> values = new ArrayList<>();
            values.add(movie.getId().toString());
            values.add(movie.getTitle());
            values.add(movie.getRating().toString());
            values.add(movie.getReleaseDate().toString());
            return values;
        }
    }
}
