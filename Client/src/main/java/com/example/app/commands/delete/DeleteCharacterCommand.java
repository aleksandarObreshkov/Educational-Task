package com.example.app.commands.delete;

import com.example.app.clients.StarWarsClient;
import com.example.app.commands.Command;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class DeleteCharacterCommand extends Command {

    private static final String ID_OPTION = "id";

    @Override
    public String getDescription() {
        return "Delete a character with the specified id";
    }

    @Override
    public String getCommandString() {
        return "delete-character";
    }

    @Override
    public Options getOptions() {
        final Options options = new Options();
        options.addOption(ID_OPTION, true, "the id of the character");
        return options;
    }

    @Override
    public void execute(String[] arguments) {
        CommandLine cmd = parseCommandLine(getOptions(), arguments);
        Long id = Long.parseLong(cmd.getOptionValue(ID_OPTION));
        StarWarsClient.characters().delete(id);
    }
}
