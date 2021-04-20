package com.example.app.commands.delete;

import com.example.app.clients.StarWarsClient;
import com.example.app.clients.StarshipClient;
import com.example.app.commands.Command;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class DeleteStarshipCommand extends Command {

    private static final String ID_OPTION = "id";
    private static final String ID_OPTION_LONG = "identifier";

    private final StarshipClient client;

    public DeleteStarshipCommand(StarshipClient client) {
        this.client = client;
    }

    public DeleteStarshipCommand() {
        client = StarWarsClient.starships();
    }

    @Override
    public String getDescription() {
        return "Delete a starship with the specified id";
    }

    @Override
    public String getCommandString() {
        return "delete-starship";
    }

    @Override
    public Options getOptions() {
        final Options options = new Options();
        options.addRequiredOption(ID_OPTION, ID_OPTION_LONG,true, "the id of the starship");
        return options;
    }

    @Override
    public void execute(String[] arguments) {
        clientOperation(arguments, cmd->{
            Long id = Long.parseLong(cmd.getOptionValue(ID_OPTION));
            client.delete(id);
        });
    }
}
