package com.example.backend.demo.services;

import com.example.backend.demo.repositories.EntityRepository;
import model.Character;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CharacterService {

    private final EntityRepository repository;

    public CharacterService(EntityRepository repository) {
        this.repository = repository;
    }

    public List<Character> getAll(){
        return repository.findAll(Character.class);
    }

    public Character getCharacterById(Long id){
        return repository.findById(id, Character.class);
    }

    public void deleteCharacterById(Long id){
        repository.deleteById(id, Character.class);
    }

    public void addCharacter(Character character){
        repository.save(character);
    }
}
