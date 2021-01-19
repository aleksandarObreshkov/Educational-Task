package com.example.app;

import com.example.app.commands.Command;
import com.example.app.commands.CommandFactory;
import com.example.app.errors.InvalidInputException;
import org.apache.commons.cli.ParseException;

public class App {
    public static void main(String[] args) {

        args = new String[]{"add-character","-n", "Palpetine", "-a", "90", "-t", "human", "-f"};
        CommandFactory factory = new CommandFactory();
        try {
            Command a = factory.createCommand(args);
            a.execute();
        } catch (InvalidInputException | ParseException ex){
            System.err.println("Invalid input: "+ex.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

