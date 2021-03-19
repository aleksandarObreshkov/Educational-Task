package com.example.backend.utils;

public class VariableConverter {

    // TODO Why doesn't this method return the result of the parsing? You'd then be able to reuse it in
    // DispatcherServlet and not have to rely on Jackson's conversion.
    public static <T> void convert(String variable, Class<T> tClass) throws IllegalArgumentException {
        if (tClass.equals(Long.class)) {
            parseLong(variable);
        }
    }

    private static void parseLong(String variable) {
        try {
            Long.valueOf(variable);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e.getMessage() + ": Should be a number.", e);
        }
    }

}
