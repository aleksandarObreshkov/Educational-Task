package com.example.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLParser {

    private static final String VALID_URL_FORMAT = "/tomcat_war_exploded/<entity>/<pathVariable>/<pathVariable>/...";

    // TODO This is still not the best name for this method. Parsing would imply reading the input and converting it to
    // a different format. Something like doUrisMatch or doesUriMatchUriTemplate would be better. Don't forget about the
    // class name as well.
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
            // TODO One comment still applies from my previous review:
            // TODO Are you trying to distinguish between methods that have the same regex, but differ based on the
            // types of
            // their path variables? For example:
            // URI: /starships/abc
            // Controller methods:
            // getById(@PathVariable("id") Long id)
            // getByName(@PathVariable("name") String name)
            //
            // You don't need to do that. This would be bad REST API design anyway. The developer should instead do it
            // like
            // this:
            // /starships/123
            // /starships?name=abc
            //
            // However, even if you're trying to implement this distinction, I don't think it's working very well right
            // now.
            // The variable converter would throw an exception if the type isn't appropriate, which wouldn't result in
            // "false" being returned by isUrlValid. Instead, the request processing would end because of the exception.
            VariableConverter.convert(pathVariableValue, pathVariableTypes.get(pathVariable));
        }
        return true;
    }

    private static boolean urlMatchesRegex(String url) {
        return url.matches(RegexUtil.METHOD_URI_REGEX);
    }

}
