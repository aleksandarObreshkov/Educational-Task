package com.example.services;

import com.example.model.Movie;
import com.example.services.deletion.MovieDeletionService;
import com.example.repositories.spring.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService extends EntityService<Movie, MovieRepository, MovieDeletionService> {
    public MovieService(MovieRepository repository, MovieDeletionService deletionService) {
        super(repository, deletionService);
    }
}
