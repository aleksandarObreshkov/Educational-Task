package com.example.app.commands;

import com.example.app.FileCreator;
import com.example.app.HelperMethods;
import com.example.app.domain.Character;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class DeleteCharacterCommand implements Command{

    private String url = "http://localhost:8080/characters/";
    private RestTemplate template;

    public DeleteCharacterCommand(String id) {
        url = url+id;
        template = new RestTemplate();
    }

    @Override
    public void execute() throws Exception {
        template.delete(url);
    }
}
