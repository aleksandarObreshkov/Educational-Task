package com.example.app.commands;

import org.springframework.web.client.RestTemplate;

public class DeleteCharacterCommand implements Command{

    private final String url;
    private final RestTemplate template;

    public DeleteCharacterCommand(String url) {
        this.url = url;
        template = new RestTemplate();
    }

    @Override
    public void execute() throws Exception {
        template.delete(url);
    }
}