package com.example.backend.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLValidator {

    private static final String VALID_URL_FORMAT = "/tomcat_war_exploded/<entity>/<pathVariable>/<pathVariable>/...";

    public static boolean isUrlValid(String uri, String uriWithPathVariablePlaceholders, Map<String, Class<?>> pathVariableTypes){
        if (!urlMatchesRegex(uri)){
            throw new IllegalArgumentException("Incorrect url. Should be: " + VALID_URL_FORMAT);
        }
        String methodRegex = RegexUtil.buildRegexString(uriWithPathVariablePlaceholders);
        Pattern pattern = Pattern.compile(methodRegex);
        Matcher matcher = pattern.matcher(uri);
        if (!matcher.matches()) {
            return false;
        }
        for (String pathVariable : pathVariableTypes.keySet()) {
            String requestVariable = matcher.group(pathVariable);
            validatePathVariable(pathVariable, requestVariable, pathVariableTypes);
        }
        return true;
    }

    private static boolean urlMatchesRegex(String url){
        return url.matches(RegexUtil.METHOD_URI_REGEX);
    }

    private static void validatePathVariable(String pathVariable, String requestVariable, Map<String, Class<?>> pathVariableTypes){
        Class<?> parsingClass = pathVariableTypes.get(pathVariable);
        VariableConverter.convert(requestVariable, parsingClass);
    }

}
