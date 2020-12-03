package com.example.app.commands;

import com.example.app.FileCreator;
import com.example.app.HelperMethods;
import com.example.app.domain.Movie;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class DeleteMovieCommand implements Command {

    private String url = "http://localhost:8080/movies/";
    private RestTemplate template;

    public DeleteMovieCommand(String id) {
        url += id;
        template = new RestTemplate();
    }

    @Override
    public void execute() throws Exception {
        template.delete(url);
    }
}
