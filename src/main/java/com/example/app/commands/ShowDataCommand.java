package com.example.app.commands;

import com.example.app.FileCreator;
import com.example.app.HelperMethods;
import com.example.app.domain.Movie;
import com.example.app.domain.Starship;
import com.example.app.domain.Character;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowDataCommand implements Command {

    private FileCreator fileCreator=new FileCreator();
    private List data = new ArrayList();

    public ShowDataCommand(String input) throws IOException {

        switch (input){
            case "movies":{
                data = HelperMethods.getDataFromFile(fileCreator.getFileMovies(), Movie.class);
                break;
            }
            case "starships":{
                data = HelperMethods.getDataFromFile(fileCreator.getFileStarships(), Starship.class);
                break;
            }
            case "characters":{
                data = HelperMethods.getDataFromFile(fileCreator.getFileCharacters(), Character.class);
                break;
            }
        }
    }

    @Override
    public void execute() throws Exception {
        HelperMethods.printData(data);
    }
}
