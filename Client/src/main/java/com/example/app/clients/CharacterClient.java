package com.example.app.clients;

import com.example.model.Character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharacterClient extends StarWarsClient{

    public CharacterClient(String url) {
        super(url);
    }

    public void delete(Long id){
        template.delete(url+id);
    }

    public void create(Character character){
        template.postForObject(url, character, Character.class);
    }

    public List<Character> list(){
        Character[] characters = template.getForObject(url, Character[].class);
        if (characters==null){
            return new ArrayList<>();
        }
        return Arrays.asList(characters);
    }
}
