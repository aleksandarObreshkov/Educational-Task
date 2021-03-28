package com.example.utils;

public class VariableConverter {

    public static <T> T convert(String variable, Class<T> tClass) throws IllegalArgumentException {
        if (tClass.equals(Long.class)) {
            return (T) parseLong(variable);
        }
        throw new IllegalArgumentException("Unsupported parsing type: "+tClass.getSimpleName());
    }

    private static Long parseLong(String variable) {
        try {
             return Long.valueOf(variable);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e.getMessage() + ": Should be a number.", e);
        }
    }

}
