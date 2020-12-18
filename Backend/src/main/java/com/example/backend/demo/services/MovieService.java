package com.example.backend.demo.services;

import com.example.backend.demo.repositories.EntityRepository;
import model.Movie;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class MovieService {

    private final EntityRepository repository;

    public MovieService(EntityRepository repository) {
        this.repository = repository;
    }

    public List<Movie> getAll() throws IOException {
        return repository.findAll(Movie.class);
    }

    public Movie getMovieById(String id){
        return repository.findById(Long.parseLong(id),Movie.class);
    }

    public void deleteMovieById(String id){
        repository.deleteById(Long.parseLong(id), Movie.class);
    }

    public void addMovie(Movie movie){
        repository.save(movie);
    }

}
