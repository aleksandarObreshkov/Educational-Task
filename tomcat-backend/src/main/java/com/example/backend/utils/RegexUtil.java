package com.example.backend.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static final String PATH_SPLITTER = "/";
    public static final String ENTITY_VARIABLE_REGEX = "(?<n>\\{[A-Za-z0-9]+\\})+";
    public static final String PATH_VARIABLE_VALUE_REGEX = "(?<n>[A-Za-z0-9]+)+";
    public static final String METHOD_URI_REGEX = "(/[a-zA-z0-9]+)*";

    private RegexUtil() {
        throw new IllegalStateException("Utility class cannot be instantiated!");
    }

    private static boolean isStringEntityVariable(String string) {
        Pattern pattern = Pattern.compile(RegexUtil.ENTITY_VARIABLE_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    public static String buildRegexString(String annotationString, List<String> pathVariables) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] urlPatterns = annotationString.split(PATH_SPLITTER);
        for (String urlPath : urlPatterns) {
            if (urlPath.isEmpty()) {
                continue;
            } else if (isStringEntityVariable(urlPath)) {
                String pathVariableName = pathVariables.get(pathVariables.indexOf(removeCurlyBraces(urlPath)));
                String namedRegex = addNameToVariablePatternRegex(pathVariableName);
                stringBuilder.append(PATH_SPLITTER).append(namedRegex);
                continue;
            }
            stringBuilder.append(PATH_SPLITTER).append(urlPath);
        }
        return stringBuilder.toString();
    }

    private static String removeCurlyBraces(String input){
        return input.substring(1, input.length()-1);
    }

    private static String addNameToVariablePatternRegex(String name){
        return PATH_VARIABLE_VALUE_REGEX.replace("n",name);
    }

}

