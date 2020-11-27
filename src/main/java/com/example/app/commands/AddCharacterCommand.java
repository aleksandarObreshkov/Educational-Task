package com.example.app.commands;

import com.example.app.FileCreator;
import com.example.app.HelperMethods;
import org.apache.commons.cli.CommandLine;

import com.example.app.domain.Character;
import java.io.IOException;
import java.util.List;

public class AddCharacterCommand implements Command {

    private FileCreator fileCreator = new FileCreator();
    private List<Character> characters;
    private CommandLine cmd;

    public AddCharacterCommand(CommandLine cmd) throws IOException {
        this.cmd = cmd;
        characters = HelperMethods.getDataFromFile(fileCreator.getFileCharacters(),Character.class);
    }

    @Override
    public void execute() throws Exception {
        try {
            characters.add(CmdCommands.createCharacter(cmd));
            HelperMethods.writeDataToFile(characters, fileCreator.getFileCharacters());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Age should be an integer: "+e.getMessage(),e);
        }
    }
}
