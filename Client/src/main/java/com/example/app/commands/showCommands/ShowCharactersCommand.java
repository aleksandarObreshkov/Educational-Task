package com.example.app.commands.showCommands;

import com.example.app.errors.RestTemplateResponseErrorHandler;
import com.example.app.printing.CharacterPrinter;
import com.example.app.commands.Command;
import model.Character;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

public class ShowCharactersCommand implements Command {

    private final List<Character> characters;

    public ShowCharactersCommand(String url) {
        RestTemplate template = new RestTemplateBuilder().errorHandler(new RestTemplateResponseErrorHandler()).build();
        // TODO Methods should have one single purpose. The purpose of the constructor should be to construct the object
        // and nothing else. Leave this work to the execute method.
        characters = Arrays.asList(template.getForObject(url, Character[].class));
    }

    public static String getDescription() {
        return "Show all characters";
    }

    public static String getCommandString() {
        return "characters";
    }

    @Override
    public void execute() {
        CharacterPrinter printer = new CharacterPrinter();
        printer.printCharacterTable(characters);
    }

}
