package com.example.app.commands.delete;

import com.example.app.clients.CharacterClient;
import com.example.app.clients.StarWarsClient;
import com.example.app.commands.Command;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class DeleteCharacterCommand extends Command {

    private static final String ID_OPTION = "id";
    private static final String ID_OPTION_LONG = "identifier";

    private final CharacterClient client;

    public DeleteCharacterCommand(CharacterClient client) {
        this.client = client;
    }

    public DeleteCharacterCommand() {
        this.client = StarWarsClient.characters();
    }

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
        options.addRequiredOption(ID_OPTION, ID_OPTION_LONG, true, "the id of the character");
        return options;
    }

    @Override
    public void execute(String[] arguments) {
        CommandLine cmd = parseCommandLine(getOptions(), arguments);
        Long id = Long.parseLong(cmd.getOptionValue(ID_OPTION));
        client.delete(id);
    }
}
