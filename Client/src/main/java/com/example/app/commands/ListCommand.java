package com.example.app.commands;

import com.example.app.commands.addCommands.AddCharacterCommand;
import com.example.app.commands.addCommands.AddMovieCommand;
import com.example.app.commands.addCommands.AddStarshipCommand;
import com.example.app.commands.deleteCommands.DeleteCharacterCommand;
import com.example.app.commands.deleteCommands.DeleteMovieCommand;
import com.example.app.commands.deleteCommands.DeleteStarshipCommand;
import com.example.app.commands.showCommands.ShowCharactersCommand;
import com.example.app.commands.showCommands.ShowMoviesCommand;
import com.example.app.commands.showCommands.ShowStarshipsCommand;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ListCommand implements Command{

    public static String getDescription(){
        return "Show all available commands";
    }

    public static String getCommandString(){
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

        for (Class<? extends Command> commandClass: commands.keySet()){
            String commandString = commandClass.getMethod("getCommandString").invoke(commandClass).toString();
            System.out.println(commandString);
            Options commandOptions = commands.get(commandClass);
            if (commandOptions!=null){
                Collection<Option> options = commands.get(commandClass).getOptions();
                for (Option option : options){
                    StringBuilder commandDetails = new StringBuilder();
                    if (option.getOpt()!=null){
                        commandDetails.append(" -").append(option.getOpt());
                    }
                    if (option.getLongOpt()!=null){
                        commandDetails.append("/-").append(option.getLongOpt());
                    }
                    if (option.getDescription()!=null){
                        commandDetails.append(" (").append(option.getDescription()).append(")");
                    }
                    System.out.println(commandDetails);
                }
            }
            String commandDescription = commandClass.getMethod("getDescription").invoke(commandClass).toString();
            System.out.println("Description: "+commandDescription);
            System.out.println();
        }
    }
}
