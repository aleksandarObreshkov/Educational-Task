package com.example.app.printing.representers;

import com.example.model.Character;
import com.example.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class CharacterMoviesRepresenter extends EntityRepresenter<Character> {

    @Override
    public List<String> getHeaderRow() {
        return List.of("Character's name", "Movie's title");
    }

    @Override
    public List<String> getRow(Character character) {
        List<String> values = new ArrayList<>();
        values.add(character.getName());
        List<String> movieTitles = createNameListFromMovies(character.getAppearsIn());
        values.add(createStringFromList(movieTitles));
        return values;
    }

    private String createStringFromList(List<String> dataList){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i< dataList.size(); i++){
            builder.append(dataList.get(i));
            if (i != dataList.size()-1){
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    private List<String> createNameListFromMovies(List<Movie> movies){
        List<String> result = new ArrayList<>();
        for (Movie movie : movies){
            result.add(movie.getTitle());
        }
        return result;
    }
}
