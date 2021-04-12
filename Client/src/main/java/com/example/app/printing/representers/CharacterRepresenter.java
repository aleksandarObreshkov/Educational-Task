package com.example.app.printing.representers;

import com.example.model.Character;
import com.example.model.Droid;

import java.util.ArrayList;
import java.util.List;

public class CharacterRepresenter extends EntityRepresenter<Character> {

    @Override
    public List<String> getHeaderRow() {
        return List.of("ID", "Name", "Age", "Force User", "Character Type", "Primary Function");
    }

    @Override
    public List<String> getRow(Character character) {
        List<String> values = new ArrayList<>();
        values.add(character.getId().toString());
        values.add(character.getName());
        values.add(character.getAge().toString());
        values.add(character.isForceUser()+"");
        values.add(character.getClass().getSimpleName());
        if (character instanceof Droid){
            Droid droid = (Droid)character;
            values.add(droid.getPrimaryFunction());
        }
        else {
            values.add("");
        }
        return values;
    }
}
