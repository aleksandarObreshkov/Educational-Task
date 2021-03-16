package com.example.app.commands;

import java.lang.reflect.InvocationTargetException;

public interface Command{
     void execute() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
