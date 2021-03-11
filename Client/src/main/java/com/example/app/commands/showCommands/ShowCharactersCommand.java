package com.example.app.commands.showCommands;

import com.example.app.utils.DataPrintingUtil;
import com.example.app.commands.Command;
import model.Character;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

public class ShowCharactersCommand implements Command {
    private final List<Character> characters;

    public ShowCharactersCommand(String url) {
        RestTemplate template = new RestTemplate();
        characters= Arrays.asList(template.getForObject(url, Character[].class));
    }

    @Override
    public void execute() {
        DataPrintingUtil.printList(characters);
    }
}
