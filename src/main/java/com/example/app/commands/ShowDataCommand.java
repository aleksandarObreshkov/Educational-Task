package com.example.app.commands;

import com.example.app.FileCreator;
import com.example.app.HelperMethods;
import com.example.app.domain.Movie;
import com.example.app.domain.Starship;
import com.example.app.domain.Character;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class ShowDataCommand implements Command {

    private RestTemplate template = new RestTemplate();
    private String url;
    private List data;

    public ShowDataCommand(String input) throws IOException {

        switch (input){
            case "movies":{
                url = "http://localhost:8080/movies";
                data=Arrays.asList(template.getForObject(url, Movie[].class));
                break;
            }
            case "starships":{
                url = "http://localhost:8080/starships";
                data = Arrays.asList(template.getForObject(url, Starship[].class));
                break;
            }
            case "characters":{
                url = "http://localhost:8080/characters";
                data = Arrays.asList(template.getForObject(url, Character[].class));
                break;
            }
        }
    }

    @Override
    public void execute() throws Exception {
        HelperMethods.printData(data);
    }
}
