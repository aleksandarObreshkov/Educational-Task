package com.example.app.commands.showCommands;

import com.example.app.utils.DataPrintingUtil;
import com.example.app.commands.Command;
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
        DataPrintingUtil.printList(starships);
    }
}
