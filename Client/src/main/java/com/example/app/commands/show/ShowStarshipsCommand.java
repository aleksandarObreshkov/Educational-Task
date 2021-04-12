package com.example.app.commands.show;

import com.example.app.clients.StarWarsClient;
import com.example.app.commands.Command;
import com.example.app.printing.printers.StarshipPrinter;
import com.example.model.Starship;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.List;


public class ShowStarshipsCommand extends Command {

    private final static Float METER_TO_FEET = 3.2808f;

    private static final String UNIT_OF_MEASUREMENT_OPTION = "u";
    private static final String UNIT_OF_MEASUREMENT_OPTION_LONG = "unit";


    @Override
    public void execute(String[] arguments) {
        CommandLine cmd = parseCommandLine(getOptions(), arguments);
        List<Starship> starships = StarWarsClient.starships().list();

        if (!cmd.hasOption(UNIT_OF_MEASUREMENT_OPTION)) {
            StarshipPrinter printer = new StarshipPrinter();
            printer.printTable(starships);
            return;
        }
        String unitOfMeasurement = cmd.getOptionValue(UNIT_OF_MEASUREMENT_OPTION);
        //This if is added in case we have other units of measurement (inches, decimeters, etc.)
        if (unitOfMeasurement.equals("imperial")){
            for (Starship starship : starships){
                starship.setLengthInMeters(starship.getLengthInMeters()*METER_TO_FEET);
            }
        }
        StarshipPrinter printer = new StarshipPrinter(unitOfMeasurement);
        printer.printTable(starships);

    }

    @Override
    public String getDescription(){
        return "Show all Starships";
    }

    @Override
    public String getCommandString(){
        return "starships";
    }
//fix length stuff
    @Override
    public Options getOptions() {
        Option unitOfMeasurement = Option.builder(UNIT_OF_MEASUREMENT_OPTION)
                .longOpt(UNIT_OF_MEASUREMENT_OPTION_LONG)
                .desc("unit of measurement: metric(default)/imperial")
                .hasArg()
                .argName("unit")
                .type(String.class)
                .build();
        Options options = new Options();
        options.addOption(unitOfMeasurement);
        return options;
    }
}
