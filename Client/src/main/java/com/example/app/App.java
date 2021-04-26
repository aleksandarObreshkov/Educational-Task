package com.example.app;

import com.example.app.commands.Command;
import com.example.app.commands.CommandFactory;
import com.example.app.errors.InvalidInputException;
import com.example.model.dto.CharacterDTO;
import com.example.model.dto.DroidDTO;
import org.apache.commons.cli.ParseException;

public class App {

    public static void main(String[] args) {
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
