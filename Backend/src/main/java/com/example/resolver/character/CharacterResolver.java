package com.example.resolver.character;

import com.example.model.Character;
import com.example.resolver.EntityResolver;
import com.example.spring_data_repositories.CharacterRepository;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CharacterResolver extends EntityResolver<Character, CharacterRepository> {

    protected CharacterResolver(CharacterRepository repository){
        super(repository);
    }

    public List<Character> allCharacters() {
        return null;//repository.findAll();
    }


    @Override
    public Iterable<Character> all() {
        return null;//repository.find();
    }

}
