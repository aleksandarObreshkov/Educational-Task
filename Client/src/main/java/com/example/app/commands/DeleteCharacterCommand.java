package com.example.app.commands;

import org.apache.commons.cli.Options;
import org.springframework.web.client.RestTemplate;

public class DeleteCharacterCommand implements Command{

    private final String url;
    private final RestTemplate template;

    public DeleteCharacterCommand(String url) {
        this.url = url;
        template = new RestTemplate();
    }

    @Override
    public void execute() {
        template.delete(url);
    }


    public static Options getDeleteOptions(){
        final  Options options = new Options();
        options.addOption("id", true, "delete an item with the specified id");
        return options;
    }
}
