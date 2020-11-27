package com.example.app.commands;

import com.example.app.FileCreator;
import com.example.app.HelperMethods;
import com.example.app.domain.Starship;

import java.util.List;

public class DeleteStarshipCommand implements Command{

    private String id;
    private List<Starship> starships;
    private final FileCreator fileCreator = new FileCreator();

    public DeleteStarshipCommand(String id) {
        this.id = id;
    }

    @Override
    public void execute() throws Exception {
        starships = HelperMethods.getDataFromFile(fileCreator.getFileStarships(), Starship.class);
        CmdCommands.deleteStarship(id, starships);
        HelperMethods.writeDataToFile(starships, fileCreator.getFileStarships());
    }
}
