package com.example.app.printing.representers;

import com.example.model.Human;
import com.example.model.Starship;

import java.util.ArrayList;
import java.util.List;

public class HumanStarshipRepresenter extends EntityRepresenter<Human> {

    @Override
    public List<String> getHeaderRow() {
        return List.of("ID", "Character's name", "Starship's name");
    }

    @Override
    public List<String> getRow(Human human) {
        List<String> values = new ArrayList<>();
        values.add(human.getId().toString());
        values.add(human.getName());
        values.add(createStringFromList(createNameListFromStarships(human.getStarships())));
        return values;
    }

    private String createStringFromList(List<String> dataList) {
        // TODO Replace this method with String.join. Also, this method is duplicated in a lot of represented. You
        // could've extracted it in some base class or a utility class.
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < dataList.size(); i++) {
            builder.append(dataList.get(i));
            if (i != dataList.size() - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    // TODO You could simplify the method name to getNames.
    private List<String> createNameListFromStarships(List<Starship> starships) {
        List<String> result = new ArrayList<>();
        for (Starship starship : starships) {
            result.add(starship.getName());
        }
        return result;
    }

}
