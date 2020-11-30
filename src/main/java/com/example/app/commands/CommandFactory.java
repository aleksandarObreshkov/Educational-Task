package com.example.app.commands;

import com.example.app.CliOptions;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import java.util.Arrays;


public class CommandFactory {

    public static Command getCommand(String[] arguments) throws Exception {
        return CliOptions.optionsSetup(arguments);
    }
}



