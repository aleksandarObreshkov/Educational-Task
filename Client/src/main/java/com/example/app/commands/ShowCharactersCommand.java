package com.example.app.commands;

import com.example.app.PrintDataMethod;
import model.Character;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

public class ShowCharactersCommand implements Command{
    private final List<Character> characters;

    public ShowCharactersCommand(String url) {
        RestTemplate template = new RestTemplate();
        characters= Arrays.asList(template.getForObject(url, Character[].class));
    }

    @Override
    public void execute() {
        PrintDataMethod.printData(characters);
    }
}
