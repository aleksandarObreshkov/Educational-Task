package com.example.app.printing.representers;

import com.example.model.Character;

import java.util.ArrayList;
import java.util.List;

public class FriendsRepresenter extends RelationshipRepresenter<Character, Character> {
    @Override
    public List<String> getHeaderRow() {
        return List.of("ID", "Character's Name", "Friend's name");
    }

    @Override
    public List<String> getRow(Character character) {
        List<String> values = new ArrayList<>();
        values.add(character.getId().toString());
        values.add(character.getName());
        values.add(createStringFromList(getNames(character.getFriends())));
        return values;
    }

    @Override
    protected List<String> getNames(List<Character> characters){
        List<String> result = new ArrayList<>();
        for (Character character : characters){
            result.add(character.getName());
        }
        return result;
    }
}
