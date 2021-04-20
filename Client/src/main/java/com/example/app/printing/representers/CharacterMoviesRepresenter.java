package com.example.app.printing.representers;

import com.example.model.Character;
import com.example.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class CharacterMoviesRepresenter extends RelationshipRepresenter<Character, Movie> {

    @Override
    public List<String> getHeaderRow() {
        return List.of("Character's name", "Movie's title");
    }

    @Override
    public List<String> getRow(Character character) {
        List<String> values = new ArrayList<>();
        values.add(character.getName());
        List<String> movieTitles = getNames(character.getAppearsIn());
        values.add(createStringFromList(movieTitles));
        return values;
    }

    @Override
    protected List<String> getNames(List<Movie> movies){
        List<String> result = new ArrayList<>();
        for (Movie movie : movies){
            result.add(movie.getTitle());
        }
        return result;
    }
}
