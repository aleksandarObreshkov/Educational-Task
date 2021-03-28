package com.example.app.commands;

import org.apache.commons.cli.HelpFormatter;
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
    public void execute(String[] arguments) {
        CommandRegistry registry = new CommandRegistry();

        for (Command command : registry.getCommands()){
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
}
