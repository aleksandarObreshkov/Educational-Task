package com.example.resolver.character;

import com.example.model.Droid;
import com.example.repositories.CharacterRepository;
import org.springframework.stereotype.Component;

@Component
public class DroidResolver extends CharacterResolver<Droid> {

    public DroidResolver() {
        super(Droid.class);
    }

    public DroidResolver(CharacterRepository<Droid> repository) {
        super(repository);
    }
}
