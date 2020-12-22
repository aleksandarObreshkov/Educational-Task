package com.example.app.commands;

import model.Starship;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.springframework.web.client.RestTemplate;

public class AddStarshipCommand implements Command {

    private final RestTemplate template;
    private final String url;
    private final CommandLine cmd;

    public AddStarshipCommand(CommandLine cmd, String url){
        this.cmd = cmd;
        this.url = url;
        this.template = new RestTemplate();
    }

    @Override
    public void execute() {
        Starship starshipToAdd = EntityCreationUtils.createStarship(cmd);
        template.postForObject(url, starshipToAdd, Starship.class);
    }

    public static Options getAddStarshipOptions(){
        final Options options = new Options();
        Option name = Option.builder("n")
                .longOpt("name")
                .hasArg()
                .required()
                .type(String.class)
                .build();
        Option length = Option.builder("l")
                .longOpt("length")
                .hasArg()
                .required()
                .type(Float.class)
                .build();

        options.addOption(name);
        options.addOption(length);
        return options;
    }
}
