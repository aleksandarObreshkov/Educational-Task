package com.example.app;
import com.example.app.commands.Command;
import com.example.app.commands.CommandFactory;

public class App 
{
    public static void main(String[] args) {
        args=new String[]{"delete-char", "-char-id", "1"};
        try {
            CliOptions.optionsSetup(args).execute();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

