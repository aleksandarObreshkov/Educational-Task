package com.example.backend.utils;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static final String PATH_SPLITTER = "/";
    public static final String PATH_VARIABLE_REGEX = "\\{[A-Za-z0-9]+\\}+";
    public static final String ACTUAL_PATH_VARIABLE_REGEX = "(?<n>[A-Za-z0-9]+)+";
    public static final String METHOD_URI_REGEX = "(/[a-zA-z0-9]+)*";

    private RegexUtil() {
        throw new IllegalStateException("Utility class cannot be instantiated!");
    }

    public static String buildRegexString(String urlWithPathVariablePlaceholders, Collection<String> pathVariables) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] pathSegments = urlWithPathVariablePlaceholders.split(PATH_SPLITTER);
        for (String pathSegment : pathSegments) {
            if (pathSegment.isEmpty()) {
                continue;
            }
            if (isPathVariablePlaceholder(pathSegment)) {
                String cleanPathSegment = removeCurlyBraces(pathSegment);
                String pathVariableName = pathVariables
                        .stream()
                        .filter(pathVariable -> pathVariable.equals(cleanPathSegment))
                        .findAny()
                        .orElse(null);
                String namedRegex = addNameToVariablePatternRegex(pathVariableName);
                stringBuilder.append(PATH_SPLITTER).append(namedRegex);
                continue;
            }
            stringBuilder.append(PATH_SPLITTER).append(pathSegment);
        }
        return stringBuilder.toString();
    }

    private static boolean isPathVariablePlaceholder(String string) {
        return string.matches(PATH_VARIABLE_REGEX);
    }

    private static String removeCurlyBraces(String input){
        return input.substring(1, input.length()-1);
    }

    private static String addNameToVariablePatternRegex(String name){
        return ACTUAL_PATH_VARIABLE_REGEX.replace("n",name);
    }

}

