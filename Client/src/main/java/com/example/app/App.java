package com.example.app;

import com.example.app.commands.Command;
import com.example.app.commands.CommandFactory;
import com.example.app.errors.InvalidInputException;
import org.apache.commons.cli.ParseException;
import org.springframework.web.client.ResourceAccessException;

public class App {

    public static void main(String[] args) {
        CommandFactory factory = new CommandFactory();
        try {
            Command commandToExecute = factory.createCommand(args);
            commandToExecute.execute();
        } catch (InvalidInputException | ParseException e) {
            System.err.println("Invalid input: " + e.getMessage());
        } catch (ResourceAccessException e) { // TODO Why have a separate catch for this and not let the one below
                                              // handle it?
            System.out.println(e.getCause().getMessage()); // TODO Print to STDERR
        } catch (Exception e) {
            System.err.print("Error: " + e.getMessage());
        }
    }

}
