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

import java.util.*;

public class CommandRegistry {

    private final List<Command> commands = new ArrayList<>();

    public CommandRegistry(){
        commands.add(new ListCommand());

        commands.add(new AddCharacterCommand());
        commands.add(new AddMovieCommand());
        commands.add(new AddStarshipCommand());

        commands.add(new DeleteCharacterCommand());
        commands.add(new DeleteMovieCommand());
        commands.add(new DeleteStarshipCommand());

        commands.add(new ShowCharactersCommand());
        commands.add(new ShowMoviesCommand());
        commands.add(new ShowStarshipsCommand());
    }

    public List<Command> getCommands(){
        return commands;
    }
}
