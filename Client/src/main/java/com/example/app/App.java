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
        } catch (InvalidInputException | ParseException ex){
            System.err.println("Invalid input: "+ex.getMessage());
        } catch (ResourceAccessException e){
            System.out.println(e.getCause().getMessage());
        } catch (Exception e) {
            System.err.print("Error: " + e.getMessage());
        }
    }
}

