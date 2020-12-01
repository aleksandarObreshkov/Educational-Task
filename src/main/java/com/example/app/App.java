package com.example.app;

public class App 
{
    public static void main(String[] args) {
        args=new String[]{"delete-char", "-char-id", "1"};
        try {
            CommandFactory.commandSetup(args).execute();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

