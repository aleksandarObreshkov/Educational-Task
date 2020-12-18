package com.example.app;

import com.example.app.commands.Command;
import com.example.app.commands.CommandFactory;
import model.Character; // TODO: Unused import. Get used to your IDE's feature to reorganize and remove unused imports and use it frequently. In Eclipse it's activated by
                        // pressing Ctrl+Shift+O.


public class App
{
    public static void main(String[] args) { // TODO: Use your IDE's formatter frequently, so that your code is formatted consistently throughout the project. 
                                             // In the long run, this makes the code much easier to read and also saves you some work:
                                             // https://medium.com/@ryconoclast/why-you-should-use-a-code-formatter-4f02dd40db14

        args = new String[]{"add-character","-n", "Tester", "-a", "90", "-t", "droid", "-pf", "Destroyer"};
        CommandFactory factory = new CommandFactory();
        Command a  = null; // TODO: Don't use one-letter variable names except for things like the counter in a for loop or a caught exception. The person 
                           // reading the code shouldn't have to keep a map like this in his head: "a is a Command object, b is a Date object that holds the current time"
        try {
            a = factory.commandSetup(args); // TODO: Declare the variable at this point and not before the try-catch block. Variables should have the smallest possible scope:
                                            // https://refactoring.com/catalog/reduceScopeOfVariable.html
            a.execute();
        } catch (Exception e) {
            e.printStackTrace(); // TODO: Users should not see stack traces. Print the exception message and log the stack trace instead:
                                 // LOGGER.error(e);
                                 // System.err.println("Error: " + e.getMessage());
        }


    }
}

