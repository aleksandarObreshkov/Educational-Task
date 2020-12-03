package com.example.app.commands;

import com.example.app.FileCreator;
import com.example.app.HelperMethods;
import org.apache.commons.cli.CommandLine;

import com.example.app.domain.Character;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

public class AddCharacterCommand implements Command {

    private RestTemplate template;
    private CommandLine cmd;
    private String url;

    public AddCharacterCommand(CommandLine cmd, String url) throws IOException {
        this.cmd = cmd;
        this.url = url;
        this.template = new RestTemplate();
    }

    @Override
    public void execute() throws Exception {
        try {
            Character characterToAdd =CmdCommands.createCharacter(cmd);
            template.postForObject(url, characterToAdd, Character.class);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Age should be an integer: "+e.getMessage(),e);
        }
    }
}
