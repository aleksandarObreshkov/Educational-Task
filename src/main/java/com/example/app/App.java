package com.example.app;

import com.example.app.commands.CommandFactory;

public class App
{
    public static void main(String[] args) {
        try{
            CommandFactory factory = new CommandFactory();
            factory.commandSetup(args).execute();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}

