package com.example.app;
import com.example.app.commands.Command;
import com.example.app.commands.CommandFactory;

public class App 
{
    public static void main(String[] args) {
        try {
            Command command = CommandFactory.getCommand(args);
            command.execute();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

