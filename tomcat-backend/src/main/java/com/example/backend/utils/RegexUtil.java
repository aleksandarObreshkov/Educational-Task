package com.example.backend.utils;

public class RegexUtil {

    public static final String PATH_SPLITTER = "/";
    public static final String PATH_VARIABLE_PLACEHOLDER_REGEX = "\\{[A-Za-z0-9]+\\}+";
    public static final String PATH_VARIABLE_VALUE_REGEX = "(?<n>[A-Za-z0-9]+)+";
    public static final String METHOD_URI_REGEX = "(/[a-zA-z0-9]+)*";

    private RegexUtil() {
        throw new IllegalStateException("Utility class cannot be instantiated!");
    }

    public static String buildRegexString(String urlWithPathVariablePlaceholders) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] pathSegments = urlWithPathVariablePlaceholders.split(PATH_SPLITTER);
        for (String pathSegment : pathSegments) {
            if (pathSegment.isEmpty()) {
                continue;
            }
            if (isPathVariablePlaceholder(pathSegment)) {
                String cleanPathSegment = removeCurlyBraces(pathSegment);
                String namedRegex = addNameToVariablePatternRegex(cleanPathSegment);
                stringBuilder.append(PATH_SPLITTER).append(namedRegex);
                continue;
            }
            stringBuilder.append(PATH_SPLITTER).append(pathSegment);
        }
        return stringBuilder.toString();
    }

    private static boolean isPathVariablePlaceholder(String string) {
        return string.matches(PATH_VARIABLE_PLACEHOLDER_REGEX);
    }

    private static String removeCurlyBraces(String input){
        return input.substring(1, input.length()-1);
    }

    private static String addNameToVariablePatternRegex(String name){
        return PATH_VARIABLE_VALUE_REGEX.replace("n",name);
    }

}

