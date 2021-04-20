package com.example.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLMatcher {

    private static final String VALID_URL_FORMAT = "/tomcat_war_exploded/<entity>/<pathVariable>/<pathVariable>/...";

    public static boolean doesUriMatchUriTemplate(String uri, String uriWithPathVariablePlaceholders) {
        if (!urlMatchesRegex(uri)) {
            throw new IllegalArgumentException("Incorrect url. Should be: " + VALID_URL_FORMAT);
        }
        String methodRegex = RegexUtil.buildPathVariableCapturingRegex(uriWithPathVariablePlaceholders);
        Pattern pattern = Pattern.compile(methodRegex);
        Matcher matcher = pattern.matcher(uri);
        return matcher.matches();
    }

    private static boolean urlMatchesRegex(String url) {
        return url.matches(RegexUtil.METHOD_URI_REGEX);
    }

}
