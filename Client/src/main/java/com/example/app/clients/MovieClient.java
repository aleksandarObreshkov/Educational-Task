package com.example.app.clients;

import com.example.model.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieClient extends StarWarsClient{

    public MovieClient(String url) {
        super(url);
    }

    public void delete(Long id){
        template.delete(url+id);
    }

    public void create(Movie movie){
         template.postForObject(url,movie, Movie.class);
    }

    public List<Movie> list(){
        Movie[] movies = template.getForObject(url, Movie[].class);
        if (movies==null){
            return new ArrayList<>();
        }
        return Arrays.asList(movies);
    }
}
