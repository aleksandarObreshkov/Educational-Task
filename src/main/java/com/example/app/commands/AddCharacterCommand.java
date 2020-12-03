package com.example.app.commands;

import org.apache.commons.cli.CommandLine;
import com.example.app.model.Character;
import org.springframework.web.client.RestTemplate;

public class AddCharacterCommand implements Command {

    private RestTemplate template;
    private CommandLine cmd;
    private String url;

    public AddCharacterCommand(CommandLine cmd, String url){
        this.cmd = cmd;
        this.url = url;
        this.template = new RestTemplate();
    }

    @Override
    public void execute() throws Exception {
        try {
            Character characterToAdd = CreateEntityFunctions.createCharacter(cmd);
            template.postForObject(url, characterToAdd, Character.class);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Age should be an integer: "+e.getMessage(),e);
        }
    }
}
