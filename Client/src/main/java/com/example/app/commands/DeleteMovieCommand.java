package com.example.app.commands;

import org.springframework.web.client.RestTemplate;

public class DeleteMovieCommand implements Command {

    private final RestTemplate template;
    private final String url;

    public DeleteMovieCommand(String url) {
        this.url=url;
        template = new RestTemplate();
    }

    @Override
    public void execute() throws Exception {
        template.delete(url);
    }
}
