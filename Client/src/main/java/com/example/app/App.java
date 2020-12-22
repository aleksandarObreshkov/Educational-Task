package com.example.app;

import com.example.app.commands.Command;
import com.example.app.commands.CommandFactory;

public class App {
    public static void main(String[] args) {
        args = new String[]{"add-character","-n", "Palpetine", "-a", "1000", "-t", "human", "-f"};
        CommandFactory factory = new CommandFactory();
        try {
            Command a = factory.createCommand(args);
            a.execute();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

