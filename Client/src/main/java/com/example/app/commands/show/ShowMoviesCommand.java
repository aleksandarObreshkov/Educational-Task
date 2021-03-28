package com.example.app.commands.show;

import com.example.app.clients.StarWarsClient;
import com.example.app.commands.Command;
import com.example.app.printing.printers.MoviePrinter;

public class ShowMoviesCommand extends Command {

    @Override
    public void execute(String[] arguments) {
        MoviePrinter printer = new MoviePrinter();
        printer.printTable(StarWarsClient.movies().list());
    }

    @Override
    public String getDescription(){
        return "Show all Movies";
    }

    @Override
    public String getCommandString(){
        return "movies";
    }
}
