package com.example.resolver.character;

import com.example.model.Human;
import com.example.repositories.CharacterRepository;
import org.springframework.stereotype.Component;

@Component
public class HumanResolver extends CharacterResolver<Human> {
    public HumanResolver() {
        super(Human.class);
    }

    public HumanResolver(CharacterRepository<Human> repository){
        super(repository);
    }
}
