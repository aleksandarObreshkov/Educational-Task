package com.example.app;

import com.example.app.commands.CommandFactory;

public class App
{
    public static void main(String[] args) {

        args = new String[]{"delete-character", "-id", "3"};
        try{
            CommandFactory.commandSetup(args).execute();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}

