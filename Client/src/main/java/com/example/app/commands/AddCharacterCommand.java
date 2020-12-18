package com.example.app.commands;

import model.Human;
import org.apache.commons.cli.CommandLine;
import model.Character;
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
            System.out.println(characterToAdd.getCharacterType());
            Class a=Character.class;
            if (characterToAdd.getCharacterType().equals("human")) a= Human.class;
            template.postForObject(url, characterToAdd, a);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Age should be an integer: "+e.getMessage(),e);
        }
    }
}
