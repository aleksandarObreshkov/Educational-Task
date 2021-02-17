package com.example.backend.utils;

public class VariableConverter {

    public static <T> void convert(String variable, Class<T> tClass) throws NumberFormatException{
        if (tClass.equals(Long.class)){
            Long.valueOf(variable);
        }
    }
}
