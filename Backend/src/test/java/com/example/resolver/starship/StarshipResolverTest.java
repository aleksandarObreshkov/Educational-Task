package com.example.resolver.starship;

import com.example.model.Starship;
import com.example.repositories.spring.StarshipRepository;
import com.example.resolver.EntityResolver;
import com.example.resolver.EntityResolverTest;
import org.mockito.Mock;

public class StarshipResolverTest extends EntityResolverTest<Starship, StarshipRepository> {

    @Mock
    StarshipRepository repository;

    @Mock
    Starship starship;

    @Override
    protected EntityResolver<Starship, StarshipRepository> initResolver(StarshipRepository repository) {
        return new StarshipResolver(repository);
    }

    @Override
    protected StarshipRepository mockRepo() {
        return repository;
    }

    @Override
    protected Starship mockEntity() {
        return starship;
    }
}
