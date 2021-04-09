package com.example.services;

import com.example.model.Movie;
import com.example.repositories.MovieRepository;
import com.example.services.deletion.MovieDeletionService;

public class MovieService extends EntityService<Movie, MovieRepository, MovieDeletionService> {
    public MovieService(MovieRepository repository, MovieDeletionService deletionService) {
        super(repository, deletionService);
    }
}
