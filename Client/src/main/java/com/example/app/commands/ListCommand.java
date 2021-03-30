package com.example.app.commands;

import com.example.app.errors.InvalidInputException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class ListCommand extends Command {

    @Override
    public String getDescription() {
        return "Show all available commands";
    }

    @Override
    public String getCommandString() {
        return "list";
    }

    @Override
    public Options getOptions() {
        Options options = new Options();
        options.addOption("c", "command", true, "print the options of the specified command");
        return options;
    }

    @Override
    public void execute(String[] arguments) {
        CommandRegistry registry = new CommandRegistry();
        CommandLine cmd = parseCommandLine(getOptions(), arguments);
        if (cmd.hasOption("c")){
            String commandString = cmd.getOptionValue("c");
            for (Command command: registry.getCommands()){
                if(command.getCommandString().equals(commandString)){
                    printCommandDetails(command);
                    return;
                }
            }
            throw new InvalidInputException("No such command: "+commandString);
        }

        for (Command command : registry.getCommands()){
            printCommandDetails(command);
        }
    }

    private void printCommandDetails(Command command){
        Options options = command.getOptions();
        String commandDescription = command.getDescription();
        String commandString = command.getCommandString();

        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.setWidth(120);
        helpFormatter.printHelp(commandString, commandDescription, options, "");
        if (!options.getOptions().isEmpty()){
            //printHelp() adds an empty line if Options is empty, but if then the new lines will be 2, so
            //I added this if to skip the repetition
            System.out.println();
        }
    }
}
