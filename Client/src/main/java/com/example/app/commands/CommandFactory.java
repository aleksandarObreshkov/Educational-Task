package com.example.app.commands;

import com.example.app.errors.InvalidInputException;
import org.apache.commons.cli.*;

public class CommandFactory {

    public Command createCommand(String[] arguments) throws ParseException {
        String mainCommand = arguments[0];
        CommandRegistry registry = new CommandRegistry();
        for (Command command : registry.getCommands()) {
            if (command.getCommandString().equals(mainCommand)){
                return command;
            }
        }
        throw new InvalidInputException("No such command: " + mainCommand, new IllegalArgumentException());
    }

}
