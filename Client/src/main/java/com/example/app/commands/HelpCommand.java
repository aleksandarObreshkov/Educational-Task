package com.example.app.commands;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class HelpCommand implements Command{

    private final CommandLine cmd;

    public HelpCommand(CommandLine cmd) {
        this.cmd = cmd;
    }

    @Override
    public void execute() {

    }

    public static Options getHelpOption(){
        final  Options options = new Options();
        options.addOption("help", true, "List all possible option arguments.");
        return options;
    }
}
