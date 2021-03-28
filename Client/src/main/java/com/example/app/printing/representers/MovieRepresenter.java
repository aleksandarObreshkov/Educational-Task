package com.example.app.printing.representers;

import com.example.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieRepresenter extends EntityRepresenter<Movie> {

    @Override
    public List<String> getHeaderRow() {
        return List.of("ID", "Title", "Rating", "Release Date");
    }

    @Override
    public List<String> getRow(Movie movie) {
        List<String> values = new ArrayList<>();
        values.add(movie.getId().toString());
        values.add(movie.getTitle());
        values.add(movie.getRating().toString());
        values.add(movie.getReleaseDate().toString());
        return values;
    }
}
