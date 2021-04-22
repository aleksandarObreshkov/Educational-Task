package com.example.resolver.character;
import com.example.model.Character;
import com.example.resolver.EntityResolver;
import com.example.repositories.spring.CharacterRepository;
import org.springframework.stereotype.Component;

@Component
public class CharacterResolver extends EntityResolver<Character, CharacterRepository> {

    protected CharacterResolver(CharacterRepository repository){
        super(repository);
    }
}
