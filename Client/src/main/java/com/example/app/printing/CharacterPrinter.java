package com.example.app.printing;

import com.example.model.*;
import com.example.model.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CharacterPrinter {

    public void printCharacterTable(List<Character> characters){
        printCharactersTable(characters);
        printFriendsTable(characters);
        printCharacterStarshipsTable(characters);
    }

    private void printCharactersTable(List<Character> characters) {
        Table characterTable = createCharacterTable(characters);
        TablePrinter.printDataTable(characterTable);
    }
    private void printFriendsTable(List<Character> characters){
        Table friendsTable = createFriendsTable(characters);
        TablePrinter.printDataTable(friendsTable);
    }
    private void printCharacterStarshipsTable(List<Character> characters){
        Table characterStarshipTable = createCharacterStarshipTable(characters);
        TablePrinter.printDataTable(characterStarshipTable);
    }

    private Table createCharacterTable(List<Character> characters){
        CharacterRepresenter representer = new CharacterRepresenter();
        Table characterTable = new Table(representer.getHeaderRow());
        for (Character character : characters){
            characterTable.getRows().add(representer.getRow(character));
        }
        return characterTable;
    }
    private Table createFriendsTable(List<Character> characters){
        FriendsRepresenter representer = new FriendsRepresenter();
        Table friendsTable = new Table(representer.getHeaderRow());
        for (Character character : characters){
            friendsTable.getRows().add(representer.getRow(character));
        }
        return friendsTable;
    }
    private Table createCharacterStarshipTable(List<Character> characters){
        List<Human> allHumans = getAllHumans(characters);
        CharacterStarshipRepresenter representer = new CharacterStarshipRepresenter();
        Table starshipsTable = new Table(representer.getHeaderRow());
        for (Human human : allHumans){
            starshipsTable.getRows().add(representer.getRow(human));
        }
        return starshipsTable;
    }

    private List<Human> getAllHumans(List<Character> characters){
        return characters.stream()
                .filter(character -> character.getCharacterType().equals("human"))
                .map(character -> (Human)character)
                .collect(Collectors.toList());
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
    private List<String> createNameListFromStarships(List<Starship> starships){
        List<String> result = new ArrayList<>();
        for (Starship starship : starships){
            result.add(starship.getName());
        }
        return result;
    }

    private class CharacterRepresenter{

        public List<String> getHeaderRow() {
            return List.of("ID", "Name", "Age", "Force User", "Character Type", "Primary Function");
        }

        public List<String> getRow(Character character) {
            List<String> values = new ArrayList<>();
            values.add(character.getId().toString());
            values.add(character.getName());
            values.add(character.getAge().toString());
            values.add(character.isForceUser()+"");
            values.add(character.getCharacterType());
            if (character instanceof Droid){
                Droid droid = (Droid)character;
                values.add(droid.getPrimaryFunction());
            }
            return values;
        }
    }

    private class FriendsRepresenter{

        public List<String> getHeaderRow() {
            return List.of("ID", "Character's Name", "Friend's name");
        }

        public List<String> getRow(Character character) {
            List<String> values = new ArrayList<>();
            values.add(character.getId().toString());
            values.add(character.getName());
            values.add(createStringFromList(createNameListFromCharacters(character.getFriends())));
            return values;
        }
    }

    private class CharacterStarshipRepresenter{

        public List<String> getHeaderRow() {
            return List.of("ID", "Character's name", "Starship's name");
        }

        public List<String> getRow(Human human) {
            List<String> values = new ArrayList<>();
            values.add(human.getId().toString());
            values.add(human.getName());
            values.add(createStringFromList(createNameListFromStarships(human.getStarships())));
            return values;
        }
    }
}
