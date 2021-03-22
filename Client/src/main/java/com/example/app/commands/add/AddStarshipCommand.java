package com.example.app.commands.add;

import com.example.app.commands.Command;
import com.example.app.errors.InvalidInputException;
import com.example.app.errors.RestTemplateResponseErrorHandler;
import com.example.model.Starship;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class AddStarshipCommand implements Command {

    private final RestTemplate template;
    private final String url;
    private final CommandLine cmd;

    public AddStarshipCommand(CommandLine cmd, String url){
        this.cmd = cmd;
        this.url = url;
        this.template = new RestTemplateBuilder()
                .errorHandler(new RestTemplateResponseErrorHandler()).build();
    }

    @Override
    public void execute() {
        Starship starshipToAdd = createStarship(cmd);
        template.postForObject(url, starshipToAdd, Starship.class);
    }

    public static String getDescription(){
        return "Add a starship to the database";
    }

    public static String getCommandString(){
        return "add-starship";
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

    private static Starship createStarship(CommandLine cmd) {
        try {
            return new Starship(cmd.getOptionValue("n"), Float.parseFloat(cmd.getOptionValue("l")));
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Length should be float.", e);
        }
    }
}
