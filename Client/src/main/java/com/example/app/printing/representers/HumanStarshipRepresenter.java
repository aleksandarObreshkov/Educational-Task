package com.example.app.printing.representers;

import com.example.model.Human;
import com.example.model.Starship;

import java.util.ArrayList;
import java.util.List;

public class HumanStarshipRepresenter extends RelationshipRepresenter<Human, Starship> {

    @Override
    public List<String> getHeaderRow() {
        return List.of("ID", "Character's name", "Starship's name");
    }

    @Override
    public List<String> getRow(Human human) {
        List<String> values = new ArrayList<>();
        values.add(human.getId().toString());
        values.add(human.getName());
        values.add(createStringFromList(getNames(human.getStarships())));
        return values;
    }

    @Override
    protected List<String> getNames(List<Starship> starships) {
        List<String> result = new ArrayList<>();
        for (Starship starship : starships) {
            result.add(starship.getName());
        }
        return result;
    }

}
