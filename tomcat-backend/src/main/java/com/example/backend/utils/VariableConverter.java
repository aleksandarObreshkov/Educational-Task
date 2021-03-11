package com.example.backend.utils;

public class VariableConverter {

    public static <T> void convert(String variable, Class<T> tClass) throws IllegalArgumentException{
        if (tClass.equals(Long.class)){
            parseLong(variable);
        }
    }

    private static void parseLong(String variable){
        try{
            Long.valueOf(variable);
        }catch (NumberFormatException e){
            throw new IllegalArgumentException(e.getMessage()+ ": Should be a number.", e);
        }
    }
}
