package com.example.app.commands.showCommands;

import com.example.app.commands.Command;
import com.example.app.printing.StarshipPrinter;
import model.Starship;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

public class ShowStarshipsCommand implements Command {

    private final List<Starship> starships;

    public ShowStarshipsCommand(String url) {
        RestTemplate template = new RestTemplate();
        starships= Arrays.asList(template.getForObject(url, Starship[].class));
    }

    @Override
    public void execute() {
        StarshipPrinter printer = new StarshipPrinter();
        printer.printStarshipsTable(starships);
    }

    public static String getDescription(){
        return "Show all starships";
    }

    public static String getCommandString(){
        return "starships";
    }
}
