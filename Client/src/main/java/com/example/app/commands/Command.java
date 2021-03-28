package com.example.app.commands;

import org.apache.commons.cli.*;

import java.util.Arrays;

public abstract class Command {

    public Options getOptions(){
        return new Options();
    }
    public abstract String getCommandString();
    public abstract String getDescription();

    public abstract void execute(String[] arguments);

    protected CommandLine parseCommandLine(Options options, String[] commandArguments) {
        CommandLineParser parser = new DefaultParser();
        String[] commandOptions = Arrays.copyOfRange(commandArguments, 1, commandArguments.length);
        try {
            return parser.parse(options, commandOptions);
        }catch (ParseException e){
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}
