package com.example.app.commands.add;

import com.example.app.commands.Command;
import com.example.app.errors.InvalidInputException;
import com.example.app.errors.RestTemplateResponseErrorHandler;
import com.example.model.Droid;
import com.example.model.Human;
import org.apache.commons.cli.CommandLine;
import com.example.model.Character;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class AddCharacterCommand implements Command {

    private final RestTemplate template;
    private final CommandLine cmd;
    private final String url;

    public AddCharacterCommand(CommandLine cmd, String url) {
        this.cmd = cmd;
        this.url = url;
        // TODO This line is duplicated in each command.
        this.template = new RestTemplateBuilder().errorHandler(new RestTemplateResponseErrorHandler()).build();
    }

    @Override
    public void execute() {
        Character characterToAdd = createCharacter(cmd);
        template.postForObject(url, characterToAdd, characterToAdd.getClass());
    }

    public static String getDescription() {
        return "Add a character to the database";
    }

    public static String getCommandString() {
        return "add-character";
    }

    public static Options getAddCharacterOptions() {
        final Options options = new Options();

        Option name = Option.builder("n").longOpt("name").hasArg().required().type(String.class).build();
        Option age = Option.builder("a").longOpt("age").hasArg().required().type(Integer.class).build();

        Option forceUser = Option.builder("f").longOpt("force-user").build();

        Option characterType = Option.builder("t")
                .longOpt("type")
                .desc("human/droid (specify primaryFunction -pf for droid)")
                .required()
                .hasArg()
                .type(String.class)
                .build();
        // TODO The name of this option uses a different naming convention than "force-user". Rename it to
        // "primary-function". Being consistent is very important. You'll be cursing a tool if you had to write "cf
        // create-service", but also "cf updateService" and "cf delete_service".
        Option primaryFunction = Option.builder("pf").longOpt("primaryFunction").hasArg().type(String.class).build();

        options.addOption(name);
        options.addOption(age);
        options.addOption(forceUser);
        options.addOption(characterType);
        options.addOption(primaryFunction);
        return options;
    }

    private static Character createCharacter(CommandLine cmd) {
        String type = cmd.getOptionValue("t");
        if (type.equals("droid")) {
            return createDroid(cmd);
        } else {
            return createHuman(cmd);
        }
    }

    private static Human createHuman(CommandLine cmd){
        try{
            Human human = new Human();
            human.setName(cmd.getOptionValue("n"));
            human.setAge(Integer.parseInt(cmd.getOptionValue("a")));
            human.setForceUser(false);
            if (cmd.hasOption("f")) {
                human.setForceUser(true);
            }
            return human;
        }catch (NumberFormatException e){
            throw new InvalidInputException("Age should be a number.", e);
        }
    }

    private static Droid createDroid(CommandLine cmd){
        try{
            Droid droid = new Droid();
            droid.setName(cmd.getOptionValue("n"));
            droid.setAge(Integer.parseInt(cmd.getOptionValue("a")));
            droid.setForceUser(false);
            droid.setPrimaryFunction(cmd.getOptionValue("pf"));
            if (cmd.hasOption("f")) {
                droid.setForceUser(true);
            }
            return droid;
        }catch (NumberFormatException e){
            throw new InvalidInputException("Age should be a number.", e);
        }
    }

}
