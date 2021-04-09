package com.example.app;

import com.example.app.commands.Command;
import com.example.app.commands.CommandFactory;
import com.example.app.errors.InvalidInputException;
import com.example.model.dto.CharacterDTO;
import com.example.model.dto.DroidDTO;
import org.apache.commons.cli.ParseException;

public class App {

    public static void main(String[] args) {
        //Left for debugging purposes and will be deleted once the project is completed
        //args = new String[]{"add-character", "-n", "SkyWalker Junior", "-a", "11", "-f", "-t", "droid", "-pf", "Be supportive"};
        //args = new String[]{"characters"};
        //args = new String[]{"list", "-c","add-movie"};
        //args = new String[]{"add-character", "-n", "Samuel L. Jackson", "-a", "35", "-f", "-t", "human", "-fr", "[2, 10]"};
        //-fr [28,29] -ap [3, 26] -st [30]
        CommandFactory factory = new CommandFactory();
        try {
            Command commandToExecute = factory.createCommand(args);
            commandToExecute.execute(args);
        } catch (InvalidInputException | ParseException e) {
            System.err.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.err.print("Error: " + e.getMessage());
        }
    }

}
