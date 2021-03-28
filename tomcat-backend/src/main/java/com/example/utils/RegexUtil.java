package com.example.utils;

public class RegexUtil {

    public static final String PATH_SPLITTER = "/";
    public static final String PATH_VARIABLE_PLACEHOLDER_REGEX = "\\{[A-Za-z0-9]+\\}";
    public static final String PATH_VARIABLE_VALUE_REGEX = "(?<n>[A-Za-z0-9]+)";
    public static final String METHOD_URI_REGEX = "(/[a-zA-z0-9]+)*";

    private RegexUtil() {
        throw new IllegalStateException("Utility class cannot be instantiated!");
    }

    public static String buildPathVariableCapturingRegex(String urlWithPathVariablePlaceholders) {
        StringBuilder result = new StringBuilder();
        String[] pathSegments = urlWithPathVariablePlaceholders.split(PATH_SPLITTER);
        for (String pathSegment : pathSegments) {
            if (pathSegment.isEmpty()) {
                continue;
            }
            if (isPathVariablePlaceholder(pathSegment)) {
                String pathVariableName = removeCurlyBraces(pathSegment);
                String pathVariableNamedRegex = addNameToVariablePatternRegex(pathVariableName);
                result.append(PATH_SPLITTER).append(pathVariableNamedRegex);
                continue;
            }
            result.append(PATH_SPLITTER).append(pathSegment);
        }
        return result.toString();
    }

    private static boolean isPathVariablePlaceholder(String pathVariable) {
        return pathVariable.matches(PATH_VARIABLE_PLACEHOLDER_REGEX);
    }

    private static String removeCurlyBraces(String placeholder) {
        return placeholder.substring(1, placeholder.length() - 1);
    }

    private static String addNameToVariablePatternRegex(String name) {
        return PATH_VARIABLE_VALUE_REGEX.replace("n", name);
    }

}
