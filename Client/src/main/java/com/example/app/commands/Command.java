package com.example.app.commands;

public interface Command{
     void execute() throws Exception; // TODO: It's not a good practice for an interface method to declare that ANY exception can be thrown from it.
                                      // https://stackoverflow.com/questions/7966148/is-throws-exception-bad-practice
}
