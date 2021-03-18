package com.example.app.commands;

import java.lang.reflect.InvocationTargetException;

public interface Command {

    // TODO You modified the interface to accommodate a quirk of a single Command - ListCommand and the fact that it's
    // using reflection. You shouldn't do that. Even if we assume that reflection was the perfect solution to the
    // problem ListCommand is trying to solve, the command should've handled these exceptions (NoSuchMethodException,
    // InvocationTargetException, IllegalAccessException) and rethrown them in a way that does not require the
    // modification of the interface (it could've wrapped them in an IllegalStateException, for example).
    void execute() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

}
