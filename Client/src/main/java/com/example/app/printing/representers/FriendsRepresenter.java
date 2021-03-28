package com.example.app.printing.representers;

import com.example.model.Character;

import java.util.ArrayList;
import java.util.List;

public class FriendsRepresenter extends EntityRepresenter<Character> {
    @Override
    public List<String> getHeaderRow() {
        return List.of("ID", "Character's Name", "Friend's name");
    }

    @Override
    public List<String> getRow(Character character) {
        List<String> values = new ArrayList<>();
        values.add(character.getId().toString());
        values.add(character.getName());
        values.add(createStringFromList(createNameListFromCharacters(character.getFriends())));
        return values;
    }

    private String createStringFromList(List<String> dataList){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i< dataList.size(); i++){
            builder.append(dataList.get(i));
            if (i != dataList.size()-1){
                builder.append(", ");
            }
        }
        return builder.toString();
    }
    private List<String> createNameListFromCharacters(List<Character> characters){
        List<String> result = new ArrayList<>();
        for (Character character : characters){
            result.add(character.getName());
        }
        return result;
    }
}
