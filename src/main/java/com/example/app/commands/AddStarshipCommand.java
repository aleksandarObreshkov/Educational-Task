package com.example.app.commands;

import com.example.app.FileCreator;
import com.example.app.HelperMethods;
import com.example.app.domain.Starship;
import org.apache.commons.cli.CommandLine;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

public class AddStarshipCommand implements Command {

    private RestTemplate template;
    private String url;
    private CommandLine cmd;

    public AddStarshipCommand(CommandLine cmd, String url) throws IOException {
        this.cmd = cmd;
        this.url = url;
        template = new RestTemplate();
    }

    @Override
    public void execute() throws Exception {
        try {
            Starship starshipToAdd = CmdCommands.createStarship(cmd);
            template.postForObject(url, starshipToAdd, Starship.class);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Length should be float: "+e.getMessage(), e);
        }
    }
}
