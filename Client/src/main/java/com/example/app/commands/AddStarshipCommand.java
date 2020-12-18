package com.example.app.commands;

import model.Starship;
import org.apache.commons.cli.CommandLine;
import org.springframework.web.client.RestTemplate;

public class AddStarshipCommand implements Command {

    private RestTemplate template;
    private String url;
    private CommandLine cmd;

    public AddStarshipCommand(CommandLine cmd, String url){
        this.cmd = cmd;
        this.url = url;
        template = new RestTemplate(); // TODO: Inconsistent use of "this."
    }

    @Override
    public void execute() throws Exception {
        try {
            Starship starshipToAdd = CreateEntityFunctions.createStarship(cmd);
            template.postForObject(url, starshipToAdd, Starship.class);
        } catch (NumberFormatException e) { // TODO: Handling exceptions at the wrong abstraction level.
            throw new IllegalArgumentException("Length should be float: " + e.getMessage(), e);
        }
    }
}
