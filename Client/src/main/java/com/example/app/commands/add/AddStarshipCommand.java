package com.example.app.commands.add;

import com.example.app.clients.StarWarsClient;
import com.example.app.clients.StarshipClient;
import com.example.app.commands.Command;
import com.example.app.errors.InvalidInputException;
import com.example.model.Starship;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.Arrays;
import java.util.List;

public class AddStarshipCommand extends Command {

    private final static Float FEET_TO_METER_COEFFICIENT = 0.3048f;

    private static final String NAME_OPTION = "n";
    private static final String LENGTH_OPTION = "l";
    private static final String UNIT_OF_MEASUREMENT_OPTION = "u";

    private static final String NAME_OPTION_LONG = "name";
    private static final String LENGTH_OPTION_LONG = "length";
    private static final String UNIT_OF_MEASUREMENT_OPTION_LONG = "unit";

    private final StarshipClient client;

    public AddStarshipCommand(StarshipClient client) {
        this.client = client;
    }

    public AddStarshipCommand(){
        this(StarWarsClient.starships());
    }


    @Override
    public void execute(String[] arguments) {
        CommandLine cmd = parseCommandLine(getOptions(), arguments);
        Starship starshipToAdd = createStarship(cmd);
        client.create(starshipToAdd);
    }

    @Override
    public String getDescription(){
        return "Add a starship to the database";
    }

    @Override
    public String getCommandString(){
        return "add-starship";
    }

    @Override
    public Options getOptions(){
        final Options options = new Options();
        Option name = Option.builder(NAME_OPTION)
                .longOpt(NAME_OPTION_LONG)
                .hasArg()
                .argName("name")
                .required()
                .type(String.class)
                .build();
        Option length = Option.builder(LENGTH_OPTION)
                .longOpt(LENGTH_OPTION_LONG)
                .hasArg()
                .argName("length")
                .type(Float.class)
                .required()
                .build();
        Option unitOfMeasurement = Option.builder(UNIT_OF_MEASUREMENT_OPTION)
                .longOpt(UNIT_OF_MEASUREMENT_OPTION_LONG)
                .desc("unit of measurement: metric(default)/imperial")
                .hasArg()
                .argName("unit")
                .type(String.class)
                .build();

        options.addOption(name);
        options.addOption(length);
        options.addOption(unitOfMeasurement);
        return options;
    }

    private static Starship createStarship(CommandLine cmd) {
        try {
            Starship starship = new Starship();
            starship.setName(cmd.getOptionValue(NAME_OPTION));
            Float length = Float.parseFloat(cmd.getOptionValue(LENGTH_OPTION));
            if (cmd.hasOption(UNIT_OF_MEASUREMENT_OPTION)){
                String unitOfMeasurement = cmd.getOptionValue(UNIT_OF_MEASUREMENT_OPTION);
                if (unitOfMeasurement.equals("imperial")){
                    starship.setLengthInMeters(length*FEET_TO_METER_COEFFICIENT);
                }
                else throw new InvalidInputException("Unrecognised unit of measurement: "+unitOfMeasurement);
            }
            return starship;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Length should be float.", e);
        }
    }
}
