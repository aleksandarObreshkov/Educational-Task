package com.example.app;

import com.example.app.commands.Command;
import com.example.app.commands.CommandFactory;
import model.Character;


public class App
{
    public static void main(String[] args) {

        args = new String[]{"add-character","-n", "Tester", "-a", "90", "-t", "droid", "-pf", "Destroyer"};
        CommandFactory factory = new CommandFactory();
        Command a  = null;
        try {
            a = factory.commandSetup(args);
            a.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

