package com.example.app.clients;

import com.example.model.Character;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CharacterClient extends EntityClient<Character> {

    public CharacterClient(String url) {
        super(url, Character.class);
    }

    @Override
    public List<Character> list() {
        Character[] characters = template.getForObject(url, Character[].class);
        if (characters == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(characters);
    }

}
