package com.example.app.commands;

import com.example.app.PrintDataMethod;
import com.example.app.model.Starship;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

public class ShowStarshipsCommand implements Command{

    private final List<Starship> starships;

    public ShowStarshipsCommand(String url) {
        RestTemplate template = new RestTemplate();
        starships= Arrays.asList(template.getForObject(url, Starship[].class));
    }

    @Override
    public void execute() throws Exception {
        PrintDataMethod.printData(starships);
    }
}
