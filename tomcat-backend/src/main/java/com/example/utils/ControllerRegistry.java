package com.example.utils;

import java.util.ArrayList;
import java.util.List;

public class ControllerRegistry{
    private static ControllerRegistry instance;
    public static ControllerRegistry getInstance() {
        if (instance == null) {
            instance = new ControllerRegistry();
        }
        return instance;
    }
    private final List<Class<?>> controllerClasses = new ArrayList<>();
    public void register(Class<?> controllerClass) {
        controllerClasses.add(controllerClass);
    }
    public List<Class<?>> getControllerClasses() {
        return controllerClasses;
    }
}
