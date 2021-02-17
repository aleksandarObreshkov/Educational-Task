package com.example.backend.utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLValidator {

    private static final String VALID_URL_FORMAT = "/tomcat_war_exploded/<entity>/<pathVariable>/<pathVariable>/...";

    public static boolean isUrlValid(String methodUriFromRequest, String methodUri, Map<String, Class<?>> pathVariableTypes){
        if (!urlMatchesRegex(methodUriFromRequest)){
            throw new IllegalArgumentException("Incorrect url. Should be: " + VALID_URL_FORMAT);
        }

        String methodRegex = RegexUtil.buildRegexString(methodUri,new ArrayList<>(pathVariableTypes.keySet()));
        Pattern pattern = Pattern.compile(methodRegex);
        Matcher matcher = pattern.matcher(methodUriFromRequest);
        if (matcher.matches()) {
            for (String pathVariable : pathVariableTypes.keySet()) {
                try {
                    String requestVariable = matcher.group(pathVariable);
                    Class<?> parsingClass = pathVariableTypes.get(pathVariable);
                    if (parsingClass.equals(String.class)) {
                        continue;
                    }
                    VariableConverter.convert(requestVariable, parsingClass);
                }catch (NumberFormatException nfe){
                    throw new IllegalArgumentException(nfe.getMessage()+": Invalid "+pathVariable);
                }
            }
            return true;
        }
        return false;
    }

    private static boolean urlMatchesRegex(String url){
        Pattern pattern = Pattern.compile(RegexUtil.METHOD_URI_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

}
