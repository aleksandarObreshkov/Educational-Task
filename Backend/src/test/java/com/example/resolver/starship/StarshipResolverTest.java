package com.example.resolver.starship;

import com.example.model.Starship;
import com.example.repositories.EntityRepository;
import com.example.repositories.StarshipRepository;
import com.example.resolver.EntityResolver;
import com.example.resolver.EntityResolverTest;
import org.mockito.Mock;
import org.mockito.Mockito;

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
