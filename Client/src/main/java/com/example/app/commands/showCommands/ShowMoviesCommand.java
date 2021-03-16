package com.example.app.commands.showCommands;

import com.example.app.utils.DataPrintingUtil;
import com.example.app.commands.Command;
import model.Movie;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

public class ShowMoviesCommand implements Command {

    private final List<Movie> movies;

    public ShowMoviesCommand(String url) {
        RestTemplate template = new RestTemplate();
        movies= Arrays.asList(template.getForObject(url, Movie[].class));
    }

    @Override
    public void execute() {
        DataPrintingUtil.printList(movies);
    }

    public static String getDescription(){
        return "Show all movies";
    }

    public static String getCommandString(){
        return "movies";
    }
}
