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
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ListCommand implements Command {

    public static String getDescription() {
        return "Show all available commands";
    }

    public static String getCommandString() {
        return "list";
    }

    @Override
    public void execute() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<Class<? extends Command>, Options> commands = new LinkedHashMap<>();
        commands.put(AddCharacterCommand.class, AddCharacterCommand.getAddCharacterOptions());
        commands.put(AddMovieCommand.class, AddMovieCommand.getAddMovieOptions());
        commands.put(AddStarshipCommand.class, AddStarshipCommand.getAddStarshipOptions());
        commands.put(DeleteCharacterCommand.class, DeleteCharacterCommand.getDeleteOptions());
        commands.put(DeleteMovieCommand.class, DeleteMovieCommand.getDeleteOptions());
        commands.put(DeleteStarshipCommand.class, DeleteStarshipCommand.getDeleteOptions());
        commands.put(ShowCharactersCommand.class, null);
        commands.put(ShowMoviesCommand.class, null);
        commands.put(ShowStarshipsCommand.class, null);
        commands.put(ListCommand.class, null);

        for (Class<? extends Command> commandClass : commands.keySet()) {
            // TODO Don't get too comfortable with Java's reflection API. Think of it as a last resort. Create a
            // registry of command descriptions instead of this and use it.
            // class CommandDescription {
            //   String name;
            //   String description;
            // }
            String commandString = commandClass.getMethod("getCommandString").invoke(commandClass).toString();
            Options commandOptions = commands.get(commandClass);
            String commandDescription = commandClass.getMethod("getDescription").invoke(commandClass).toString();

            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp(commandString, "", commandOptions, commandDescription);
            System.out.println();
        }
    }

}
