package com.example.resolver.movie;

import com.example.model.Movie;
import com.example.spring_data_repositories.MovieRepository;
import com.example.resolver.EntityResolver;
import com.example.resolver.EntityResolverTest;
import org.mockito.Mock;
import org.mockito.Mockito;

public class MovieResolverTest extends EntityResolverTest<Movie, MovieRepository> {

    @Mock
    MovieRepository repository;

    @Mock
    Movie movie;

    @Override
    protected EntityResolver<Movie, MovieRepository> initResolver(MovieRepository repository) {
        return new MovieResolver(repository);
    }

    @Override
    protected MovieRepository mockRepo() {
        return repository;
    }

    @Override
    protected Movie mockEntity() {
        return movie;
    }
}
