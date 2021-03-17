package com.example.app.printing;

import model.Character;
import model.Droid;
import model.Human;
import model.Starship;

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
        String[][] characterTable = createCharacterTableArray(characters);
        TablePrinter.printDataTable(characterTable);
    }
    private void printFriendsTable(List<Character> characters){
        String[][] friendsTable = createFriendsTable(characters);
        TablePrinter.printDataTable(friendsTable);
    }
    private void printCharacterStarshipsTable(List<Character> characters){
        String[][] characterStarshipTable = createCharacterStarshipTable(characters);
        TablePrinter.printDataTable(characterStarshipTable);
    }

    private String[][] createCharacterTableArray(List<Character> characters){
        String[][] characterTable = new String[characters.size()+1][6];
        characterTable[0][0] = "Id";
        characterTable[0][1] = "Name";
        characterTable[0][2] = "Age";
        characterTable[0][3] = "Force User";
        characterTable[0][4] = "Character Type";
        characterTable[0][5] = "Primary function";

        for (int i = 1; i< characters.size()+1; i++){
            characterTable[i][0] = characters.get(i-1).getId().toString();
            characterTable[i][1] = characters.get(i-1).getName();
            characterTable[i][2] = characters.get(i-1).getAge().toString();
            characterTable[i][3] = characters.get(i-1).isForceUser()+"";
            characterTable[i][4] = characters.get(i-1).getCharacterType();
            characterTable[i][5] = "";
            if (characters.get(i-1).getClass().equals(Droid.class)){
                Droid tempDroid = (Droid)characters.get(i-1);
                characterTable[i][5] = tempDroid.getPrimaryFunction();
            }
        }
        return characterTable;
    }
    private String[][] createFriendsTable(List<Character> characters){
        String[][] friendsTable = new String[characters.size()+1][3];
        friendsTable[0][0] = "Id";
        friendsTable[0][1] = "Character's Name";
        friendsTable[0][2] = "Friend's Name";

        for (int i = 1; i< characters.size()+1; i++){
            friendsTable[i][0] = characters.get(i-1).getId().toString();
            friendsTable[i][1] = characters.get(i-1).getName();
            friendsTable[i][2] = createStringFromList(createNameListFromCharacters(characters.get(i-1).getFriends()));
        }
        return friendsTable;
    }
    private String[][] createCharacterStarshipTable(List<Character> characters){
        List<Human> allHumans = getAllHumans(characters);
        String[][] starshipsTable = new String[allHumans.size()+1][3];
        starshipsTable[0][0] = "Id";
        starshipsTable[0][1] = "Character's Name";
        starshipsTable[0][2] = "Starship's Name";

        for (int i = 1; i< allHumans.size()+1; i++){
            starshipsTable[i][0] = allHumans.get(i-1).getId().toString();
            starshipsTable[i][1] = allHumans.get(i-1).getName();
            starshipsTable[i][2] = createStringFromList(createNameListFromStarships(allHumans.get(i-1).getStarships()));
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
}
