package com.example.backend.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLValidator {

    private static final String VALID_URL_FORMAT = "/tomcat_war_exploded/<entity>/<pathVariable>/<pathVariable>/...";

    // TODO This method does much more than just validating the URI. Use a better name. Also make sure to revisit the
    // class name after the rename.
    public static boolean isUrlValid(String uri, String uriWithPathVariablePlaceholders,
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
            // TODO Rename to pathVariableValue.
            String requestVariable = matcher.group(pathVariable);
            // TODO Remove this method and just call VariableConverter.convert(pathVariableValue,
            // pathVariableType.get(pathVariable));
            validatePathVariable(pathVariable, requestVariable, pathVariableTypes);
        }
        return true;
    }

    private static boolean urlMatchesRegex(String url) {
        return url.matches(RegexUtil.METHOD_URI_REGEX);
    }

    private static void validatePathVariable(String pathVariable, String requestVariable,
                                             Map<String, Class<?>> pathVariableTypes) {
        Class<?> parsingClass = pathVariableTypes.get(pathVariable);
        // TODO Are you trying to distinguish between methods that have the same regex, but differ based on the types of
        // their path variables? For example:
        // URI: /starships/abc
        // Controller methods:
        // getById(@PathVariable("id") Long id)
        // getByName(@PathVariable("name") String name)
        //
        // You don't need to do that. This would be bad REST API design anyway. The developer should instead do it like
        // this:
        // /starships/123
        // /starships?name=abc
        //
        // However, even if you're trying to implement this distinction, I don't think it's working very well right now.
        // The variable converter would throw an exception if the type isn't appropriate, which wouldn't result in
        // "false" being returned by isUrlValid. Instead, the request processing would end because of the exception.
        VariableConverter.convert(requestVariable, parsingClass);
    }

}
