package com.example.app.commands.addCommands;

import com.example.app.commands.Command;
import com.example.app.commands.EntityCreationUtils;
import org.apache.commons.cli.CommandLine;
import model.Character;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.springframework.web.client.RestTemplate;

public class AddCharacterCommand implements Command {

    private final RestTemplate template;
    private final CommandLine cmd;
    private final String url;

    public AddCharacterCommand(CommandLine cmd, String url){
        this.cmd = cmd;
        this.url = url;
        this.template = new RestTemplate();
    }

    @Override
    public void execute() {
        Character characterToAdd = EntityCreationUtils.createCharacter(cmd);
        template.postForObject(url, characterToAdd, characterToAdd.getClass());
    }

    public static String getDescription(){
        return "Add a character to the database";
    }

    public static String getCommandString(){
        return "add-character";
    }

    public static Options getAddCharacterOptions(){
        final Options options = new Options();

        Option name = Option.builder("n")
                .longOpt("name")
                .hasArg()
                .required()
                .type(String.class)
                .build();
        Option age = Option.builder("a")
                .longOpt("age")
                .hasArg()
                .required()
                .type(Integer.class)
                .build();

        Option forceUser = Option.builder("f")
                .longOpt("force-user")
                .build();

        Option characterType = Option.builder("t")
                .longOpt("type")
                .desc("human/droid (specify primaryFunction -pf for droid)")
                .required()
                .hasArg()
                .type(String.class)
                .build();
        Option primaryFunction = Option.builder("pf")
                .longOpt("primaryFunction")
                .hasArg()
                .type(String.class)
                .build();

        options.addOption(name);
        options.addOption(age);
        options.addOption(forceUser);
        options.addOption(characterType);
        options.addOption(primaryFunction);
        return options;
    }
}
