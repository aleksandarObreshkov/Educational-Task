package com.example.backend.demo.services;

import com.example.backend.demo.FileCreator;
import com.example.backend.demo.HelperMethods;
import model.Movie;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class MovieService {

    private final FileCreator fileCreator = new FileCreator();

    public List<Movie> getAll() throws IOException {
        return HelperMethods.getDataFromFile(fileCreator.getFileMovies(), Movie.class);
    }

    public Movie getMovieById(String id) throws IOException{
        List<Movie> movies = getAll();
        for(Movie a:movies){
            if (a.getId().equals(id))return a;
        }
        throw new IOException("No movie with the specified id.");
    }

    public void deleteMovieById(String id) throws IOException{
        List<Movie> movies = getAll();
        int index;
        for(Movie a:movies){
            if (a.getId().equals(id)){
                index=movies.indexOf(a);
                movies.remove(index);
                HelperMethods.writeDataToFile(movies,fileCreator.getFileMovies());
                return;
            }
        }
        throw new IOException("No movie with the specified id.");

    }

    public void addMovie(Movie movie) throws IOException{
        List<Movie> movies = HelperMethods.getDataFromFile(fileCreator.getFileMovies(), Movie.class);
        movie.setId(UUID.randomUUID().toString());
        movies.add(movie);
        HelperMethods.writeDataToFile(movies,fileCreator.getFileMovies());
    }

}
