package com.example;

import com.example.app.commands.add.AddCharacterCommand;
import com.example.model.Character;
import org.apache.commons.cli.CommandLine;

public class CreateCharacter extends AddCharacterCommand {

    public static Character createCharacter(CommandLine cmd){
        return createCharacterDTO(cmd);
    }
}
