package com.example.resolver.starship;

import com.example.repositories.spring.StarshipRepository;
import com.example.resolver.EntityResolver;
import com.example.model.Starship;
import org.springframework.stereotype.Component;

@Component
public class StarshipResolver extends EntityResolver<Starship, StarshipRepository>{

    public StarshipResolver(StarshipRepository repository){
        super(repository);
    }

}
