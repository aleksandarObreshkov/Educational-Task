package com.example.app;

import com.example.app.commands.CommandFactory;

public class App
{
    public static void main(String[] args) {

        args = new String[]{"add-movie","-t", "H", "-r", "ww", "-d", "2008-08-20"};
        try{
            CommandFactory factory = new CommandFactory();
            factory.commandSetup(args).execute();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}

