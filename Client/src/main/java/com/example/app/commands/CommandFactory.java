package com.example.app.commands;

import com.example.app.commands.add.AddCharacterCommand;
import com.example.app.commands.add.AddMovieCommand;
import com.example.app.commands.add.AddStarshipCommand;
import com.example.app.commands.delete.DeleteCharacterCommand;
import com.example.app.commands.delete.DeleteMovieCommand;
import com.example.app.commands.delete.DeleteStarshipCommand;
import com.example.app.commands.show.ShowCharactersCommand;
import com.example.app.commands.show.ShowMoviesCommand;
import com.example.app.commands.show.ShowStarshipsCommand;
import com.example.app.errors.InvalidInputException;
import org.apache.commons.cli.*;
import java.util.Arrays;

public class CommandFactory {

    public Command createCommand(String[] arguments) throws ParseException {
        final String url = "http://localhost:8080/";
        String mainCommand = arguments[0];
        String[] commandOptions = Arrays.copyOfRange(arguments, 1, arguments.length);
        switch (mainCommand) {
            case "characters":
                return new ShowCharactersCommand(url + mainCommand);
            case "movies":
                return new ShowMoviesCommand(url + mainCommand);
            case "starships":
                // TODO What if you decide to rename the command in the future? Maybe some user complains that
                // "starships" is too long and demands "ships" instead. Maybe you want to add a short name for the
                // command - "s", for example. The URL would then be incorrect. You should not rely on the fact that
                // currently the command name matches the endpoint.
                return new ShowStarshipsCommand(url + mainCommand);
            case "add-movie": {
                CommandLine cmd = parseCommandLine(AddMovieCommand.getAddMovieOptions(), commandOptions);
                // TODO You're missing a level of abstraction. The factory being concerned with building the URL the
                // command will call is a little too much responsibility for it.
                // Also, what if a command must call two different endpoints? For example, creating a character, making
                // him a friend of another character, and then finally making the second character a friend of the
                // first. That's at least two calls with different URLs.
                // You can fix this by creating a client - StarWarsClient - with methods like:
                // client.movies().list()
                // client.humans().create(human);
                // client.droids().delete(id);
                return new AddMovieCommand(cmd, url + "movies");
            }
            case "add-character": {
                // TODO There's some duplication here that can be fixed fairly easily. The getXXXOptions methods are
                // static, because they need to be accessed before an instance is created. But what if the command
                // constructor accepts an array of string arguments instead? You could then use the template method
                // pattern in an abstract command class to parse the options with the help of the Commons CLI API and
                // pass them to the command implementations.
                CommandLine cmd = parseCommandLine(AddCharacterCommand.getAddCharacterOptions(), commandOptions);
                return new AddCharacterCommand(cmd, url + "characters");
            }
            case "add-starship": {
                CommandLine cmd = parseCommandLine(AddStarshipCommand.getAddStarshipOptions(), commandOptions);
                return new AddStarshipCommand(cmd, url + "starships");
            }
            case "delete-character": {
                CommandLine cmd = parseCommandLine(DeleteCharacterCommand.getDeleteOptions(), commandOptions);
                String newUrl = url + "characters/" + cmd.getOptionValue("id");
                return new DeleteCharacterCommand(newUrl);
            }
            case "delete-starship": {
                CommandLine cmd = parseCommandLine(DeleteStarshipCommand.getDeleteOptions(), commandOptions);
                String newUrl = url + "starships/" + cmd.getOptionValue("id");
                return new DeleteStarshipCommand(newUrl);
            }
            case "delete-movie": {
                CommandLine cmd = parseCommandLine(DeleteMovieCommand.getDeleteOptions(), commandOptions);
                String newUrl = url + "movies/" + cmd.getOptionValue("id");
                return new DeleteMovieCommand(newUrl);
            }
            case "list": {
                return new ListCommand();
            }
            default:
                throw new InvalidInputException("No such command: " + mainCommand, new IllegalArgumentException());
        }
    }

    private CommandLine parseCommandLine(Options options, String[] commandArguments) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, commandArguments);
    }

}
