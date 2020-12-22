package com.example.app.commands;

import com.example.app.PrintDataMethod;
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
        PrintDataMethod.printData(movies);
    }
}
