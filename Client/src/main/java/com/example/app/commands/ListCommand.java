package com.example.app.commands;

import com.example.app.commands.addCommands.AddCharacterCommand;
import com.example.app.commands.addCommands.AddMovieCommand;
import com.example.app.commands.addCommands.AddStarshipCommand;
import com.example.app.commands.deleteCommands.DeleteCharacterCommand;
import com.example.app.commands.deleteCommands.DeleteMovieCommand;
import com.example.app.commands.deleteCommands.DeleteStarshipCommand;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.*;

public class ListCommand implements Command{

    @Override
    public void execute() {
        Map<String, Options> commands = new LinkedHashMap<>();
        commands.put("add-character", AddCharacterCommand.getAddCharacterOptions());
        commands.put("add-movie", AddMovieCommand.getAddMovieOptions());
        commands.put("add-starship", AddStarshipCommand.getAddStarshipOptions());
        commands.put("delete-character", DeleteCharacterCommand.getDeleteOptions());
        commands.put("delete-movie", DeleteMovieCommand.getDeleteOptions());
        commands.put("delete-starship", DeleteStarshipCommand.getDeleteOptions());
        commands.put("characters", null);
        commands.put("movies", null);
        commands.put("starships", null);

        for (String commandString : commands.keySet()){
            System.out.print(commandString);
            Options commandOptions = commands.get(commandString);
            if (commandOptions==null){
                System.out.println();
                continue;
            }
            Collection<Option> options = commands.get(commandString).getOptions();
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
                System.out.print(commandDetails);
            }
            System.out.println();
        }
    }
}
