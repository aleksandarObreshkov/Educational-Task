package com.example.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLParser {

    private static final String VALID_URL_FORMAT = "/tomcat_war_exploded/<entity>/<pathVariable>/<pathVariable>/...";

    public static boolean parseUrl(String uri, String uriWithPathVariablePlaceholders,
                                   Map<String, Class<?>> pathVariableTypes) {
        if (!urlMatchesRegex(uri)) {
            throw new IllegalArgumentException("Incorrect url. Should be: " + VALID_URL_FORMAT);
        }
        String methodRegex = RegexUtil.buildPathVariableCapturingRegex(uriWithPathVariablePlaceholders);
        Pattern pattern = Pattern.compile(methodRegex);
        Matcher matcher = pattern.matcher(uri);
        if (!matcher.matches()) {
            return false;
        }
        for (String pathVariable : pathVariableTypes.keySet()) {
            String pathVariableValue = matcher.group(pathVariable);
            VariableConverter.convert(pathVariableValue, pathVariableTypes.get(pathVariable));
        }
        return true;
    }

    private static boolean urlMatchesRegex(String url) {
        return url.matches(RegexUtil.METHOD_URI_REGEX);
    }

}
