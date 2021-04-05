package com.example.app;

import com.example.app.commands.Command;
import com.example.app.commands.CommandFactory;
import com.example.app.errors.InvalidInputException;
import org.apache.commons.cli.ParseException;

public class App {

    public static void main(String[] args) {
        //Left for debugging purposes and will be deleted once the project is completed.

        //args = new String[]{"add-character", "-n", "Jar Jar Binks", "-a", "24", "-f", "-t", "droid", "-pf", "Be supportive","-fr", "[502, 503]"};
        //args = new String[]{"characters"};
        //args = new String[]{"list", "-c","add-movie"};
        //args = new String[]{"add-character", "-n", "Padme", "-a", "21", "-t", "human", "-ap", "[902]"};
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
