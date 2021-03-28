package com.example.app.commands.delete;

import com.example.app.clients.StarWarsClient;
import com.example.app.commands.Command;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class DeleteMovieCommand extends Command {

    @Override
    public String getDescription() {
        return "Delete a movie with the specified id";
    }

    @Override
    public String getCommandString() {
        return "delete-movie";
    }

    @Override
    public Options getOptions() {
        final Options options = new Options();
        options.addOption("id", true, "the id of the movie");
        return options;
    }

    @Override
    public void execute(String[] arguments) {
        CommandLine cmd = parseCommandLine(getOptions(), arguments);
        Long id = Long.parseLong(cmd.getOptionValue("id"));
        StarWarsClient.movies().delete(id);
    }
}
