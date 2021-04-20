package com.example.app.clients;

import com.example.model.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MovieClient extends EntityClient<Movie>{

    public MovieClient(String url) {
        super(url, Movie.class);
    }

    @Override
    public List<Movie> list(){
        Movie[] movies = template.getForObject(url, Movie[].class);
        if (movies==null){
            return Collections.emptyList();
        }
        return Arrays.asList(movies);
    }
}
