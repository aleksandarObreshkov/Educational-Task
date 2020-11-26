package com.example.app.commands;

import com.example.app.FileCreator;
import com.example.app.HelperMethods;
import com.example.app.domain.Character;
import com.example.app.domain.Movie;
import com.example.app.domain.Starship;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteCommand implements Command {

    private String id;
    private String input;
    private FileCreator fileCreator=new FileCreator();
    private List data = new ArrayList<>();

    public DeleteCommand(String input,String id){
        this.id=id;
        this.input=input;
    }

    @Override
    public void execute() throws Exception {
        try {
            switch (input) {
                case "delete-movie": {
                    data = HelperMethods.getDataFromFile(fileCreator.getFileMovies(), Movie.class);
                    CmdCommands.deleteMovie(id, data);
                    HelperMethods.writeDataToFile(data, fileCreator.getFileMovies());
                    break;
                }
                case "delete-starship": {
                    data = HelperMethods.getDataFromFile(fileCreator.getFileStarships(), Starship.class);
                    CmdCommands.deleteStarship(id, data);
                    HelperMethods.writeDataToFile(data, fileCreator.getFileStarships());
                    break;
                }
                case "delete-character": {
                    data = HelperMethods.getDataFromFile(fileCreator.getFileCharacters(), Character.class);
                    CmdCommands.deleteCharacter(id, data);
                    HelperMethods.writeDataToFile(data, fileCreator.getFileCharacters());
                    break;
                }
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Id should be an integer: " + e.getMessage());
        }
    }

}

