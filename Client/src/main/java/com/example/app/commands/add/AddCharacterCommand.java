package com.example.app.commands.add;

import com.example.app.clients.StarWarsClient;
import com.example.app.commands.Command;
import com.example.app.errors.InvalidInputException;
import com.example.model.*;
import com.example.model.Character;
import com.example.model.dto.CharacterDTO;
import com.example.model.dto.DroidDTO;
import com.example.model.dto.HumanDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AddCharacterCommand extends Command {

    private static final String NAME_OPTION = "n";
    private static final String AGE_OPTION = "a";
    private static final String FORCE_USER_OPTION = "f";
    private static final String PRIMARY_FUNCTION_OPTION = "pf";
    private static final String FRIENDS_OPTION = "fr";
    private static final String APPEARS_IN_OPTION = "ap";
    private static final String STARSHIPS_OPTION = "st";
    private static final String CHARACTER_TYPE_OPTION = "t";

    private static final String NAME_OPTION_LONG = "name";
    private static final String AGE_OPTION_LONG = "age";
    private static final String FORCE_USER_OPTION_LONG = "force-user";
    private static final String PRIMARY_FUNCTION_OPTION_LONG = "primary-function";
    private static final String FRIENDS_OPTION_LONG = "friends";
    private static final String APPEARS_IN_OPTION_LONG = "appears-in";
    private static final String STARSHIPS_OPTION_LONG = "starships";
    private static final String CHARACTER_TYPE_OPTION_LONG = "type";

    @Override
    public void execute(String[] arguments) {
        CommandLine cmd = parseCommandLine(getOptions(), arguments);
        Character characterToAdd = createCharacterDTO(cmd);
        StarWarsClient.characters().create(characterToAdd);
    }

    @Override
    public String getDescription() {
        return "Add a character to the database";
    }

    @Override
    public String getCommandString() {
        return "add-character";
    }

    @Override
    public Options getOptions() {
        Options options = new Options();

        Option name = Option.builder(NAME_OPTION).longOpt(NAME_OPTION_LONG).hasArg().required().type(String.class).build();
        Option age = Option.builder(AGE_OPTION).longOpt(AGE_OPTION_LONG).hasArg().required().type(Integer.class).build();

        Option forceUser = Option.builder(FORCE_USER_OPTION).longOpt(FORCE_USER_OPTION_LONG).build();

        Option characterType = Option.builder(CHARACTER_TYPE_OPTION)
                .longOpt(CHARACTER_TYPE_OPTION_LONG)
                .desc("human/droid (specify primaryFunction -"+PRIMARY_FUNCTION_OPTION+" for droid)")
                .required()
                .hasArg()
                .type(String.class)
                .build();

        Option primaryFunction = Option.builder(PRIMARY_FUNCTION_OPTION).longOpt(PRIMARY_FUNCTION_OPTION_LONG).hasArg().type(String.class).build();

        Option friends = Option
                .builder(FRIENDS_OPTION)
                .longOpt(FRIENDS_OPTION_LONG)
                .desc("id's of the character's friends i.e [<id>, <id>, ...]")
                .hasArg()
                .type(Character[].class)
                .build();

        Option appearsIn = Option
                .builder(APPEARS_IN_OPTION)
                .longOpt(APPEARS_IN_OPTION_LONG)
                .desc("id's of the movies in which the character appears i.e [<id>, <id>, ...]")
                .hasArg()
                .type(Movie[].class)
                .build();

        Option starships = Option
                .builder(STARSHIPS_OPTION)
                .longOpt(STARSHIPS_OPTION_LONG)
                .desc("id's of the starships that the character has(applicable to 'human' only) i.e [<id>, <id>, ...]")
                .hasArg()
                .type(Starship[].class)
                .build();

        options.addOption(name);
        options.addOption(age);
        options.addOption(forceUser);
        options.addOption(characterType);
        options.addOption(primaryFunction);
        options.addOption(friends);
        options.addOption(appearsIn);
        options.addOption(starships);
        return options;
    }

    private static void fillCharacterDTO(CommandLine cmd, CharacterDTO dto) {
        try{
            dto.setName(cmd.getOptionValue(NAME_OPTION));
            dto.setAge(Integer.parseInt(cmd.getOptionValue(AGE_OPTION)));
            dto.setForceUser(false);
            if (cmd.hasOption(FORCE_USER_OPTION)) {
                dto.setForceUser(true);
            }
            if (cmd.hasOption(FRIENDS_OPTION)) {
                String friendIdsString = cmd.getOptionValue(FRIENDS_OPTION);
                dto.setFriendIds(getIds(friendIdsString));
            }
            if (cmd.hasOption(APPEARS_IN_OPTION)) {
                String movieIdsString = cmd.getOptionValue(APPEARS_IN_OPTION);
                dto.setMovieIds(getIds(movieIdsString));
            }
        }catch (NumberFormatException e){
            throw new InvalidInputException("Age should be a number.", e);
        }
    }

    private static HumanDTO createHuman(CommandLine cmd){
        HumanDTO humanDTO = new HumanDTO();
        fillCharacterDTO(cmd, humanDTO);
        if (cmd.hasOption(STARSHIPS_OPTION)) {
            String starshipIdsString = cmd.getOptionValue(STARSHIPS_OPTION);
            humanDTO.setStarshipsIds(getIds(starshipIdsString));
        }
        return humanDTO;
    }

    private static DroidDTO createDroid(CommandLine cmd){
        DroidDTO droidDTO = new DroidDTO();
        fillCharacterDTO(cmd, droidDTO);
        droidDTO.setPrimaryFunction(cmd.getOptionValue(PRIMARY_FUNCTION_OPTION));
        return droidDTO;
    }

    private static CharacterDTO createCharacterDTO(CommandLine cmd){
        String type = cmd.getOptionValue(CHARACTER_TYPE_OPTION);
        if (type.equals("droid")) {
            return createDroid(cmd);
        } else {
            return createHuman(cmd);
        }
    }

    private static List<Long> getIds(String idString){
        try{
            Long[] ids = new ObjectMapper().readValue(idString, Long[].class);
            return Arrays.asList(ids);
        }catch (IOException e){
            throw new IllegalArgumentException("Incorrect format for ids: should be [<id>, <id>,...]");
        }
    }

}
