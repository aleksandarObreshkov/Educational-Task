package com.example.app.commands;

import com.example.app.FileCreator;
import com.example.app.HelperMethods;
import com.example.app.domain.Starship;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import java.util.List;

public class AddStarshipCommand implements Command {

    private FileCreator fileCreator = new FileCreator();
    private List<Starship> starships;
    private CommandLine cmd;

    public AddStarshipCommand(CommandLine cmd) throws IOException {
        this.cmd = cmd;
        this.starships = HelperMethods.getDataFromFile(fileCreator.getFileStarships(),Starship.class);
    }

    @Override
    public void execute() throws Exception {
        try {
            starships.add(CmdCommands.createStarship(cmd));
            HelperMethods.writeDataToFile(starships, fileCreator.getFileStarships());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Length should be float: "+e.getMessage());
        }
    }
}
