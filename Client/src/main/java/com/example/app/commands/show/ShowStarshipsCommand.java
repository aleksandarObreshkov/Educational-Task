package com.example.app.commands.show;

import com.example.app.commands.Command;
import com.example.app.errors.RestTemplateResponseErrorHandler;
import com.example.app.printing.StarshipPrinter;
import com.example.model.Starship;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

public class ShowStarshipsCommand implements Command {

    private final List<Starship> starships;

    public ShowStarshipsCommand(String url) {
        RestTemplate template = new RestTemplateBuilder()
                .errorHandler(new RestTemplateResponseErrorHandler()).build();
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
