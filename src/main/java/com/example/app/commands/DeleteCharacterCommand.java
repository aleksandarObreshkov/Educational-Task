package com.example.app.commands;

import com.example.app.FileCreator;
import com.example.app.HelperMethods;
import com.example.app.domain.Character;

import java.util.List;

public class DeleteCharacterCommand implements Command{

    private String id;
    private List<Character> characters;
    private final FileCreator fileCreator = new FileCreator();

    public DeleteCharacterCommand(String id) {
        this.id = id;
    }

    @Override
    public void execute() throws Exception {
        characters = HelperMethods.getDataFromFile(fileCreator.getFileCharacters(), Character.class);
        CmdCommands.deleteCharacter(id, characters);
        HelperMethods.writeDataToFile(characters, fileCreator.getFileCharacters());
    }
}
