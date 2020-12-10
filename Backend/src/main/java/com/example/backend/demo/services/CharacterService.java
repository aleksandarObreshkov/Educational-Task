package com.example.backend.demo.services;

import com.example.backend.demo.FileCreator;
import com.example.backend.demo.HelperMethods;
import model.Character;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class CharacterService {

    private final FileCreator fileCreator = new FileCreator();

    public List<Character> getAll() throws IOException{
        return HelperMethods.getDataFromFile(fileCreator.getFileCharacters(), Character.class);
    }

    public Character getCharacterById(String id) throws IOException{
        List<Character> characters = getAll();
        for(Character a : characters){
            if (a.getId().equals(id))return a;
        }
        throw new IOException("No character with the specified id.");
    }

    public void deleteCharacterById(String id) throws IOException{
        List<Character> characters = HelperMethods.getDataFromFile(fileCreator.getFileCharacters(), Character.class);
        int index;
        for (Character a : characters) {
            if (a.getId().equals(id)) {
                index = characters.indexOf(a);
                characters.remove(index);
                HelperMethods.writeDataToFile(characters, fileCreator.getFileCharacters());
                return;
            }
        }
        throw new IOException("No character with the specified id.");


    }

    public void addCharacter(Character character) throws IOException{
        List<Character> characters = HelperMethods.getDataFromFile(fileCreator.getFileCharacters(), Character.class);
        character.setId(UUID.randomUUID().toString());
        characters.add(character);
        HelperMethods.writeDataToFile(characters,fileCreator.getFileCharacters());
    }
}
