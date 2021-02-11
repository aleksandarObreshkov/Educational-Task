package com.example.backend.RESTEntities;

import com.example.backend.constants.HttpMethod;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RequestEntityProvider {

    private final HttpServletRequest request;

    public RequestEntityProvider(HttpServletRequest request) {
        this.request = request;
    }

    public RequestEntity createRequestEntity() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, InstantiationException {

        Pattern pattern = Pattern.compile("/tomcat_backend_war_exploded/[a-zA-z]+/*(?<id>[0-9]*)/?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(removeTrailingSlash(request.getRequestURI()));
        HttpMethod requestMethod = HttpMethod.valueOf(request.getMethod());
        RequestEntity result = new RequestEntity(requestMethod, request.getRequestURI());
        if (matcher.matches()){
            if (!matcher.group("id").isEmpty()){
                Map<String, String> pathVariables = new HashMap<>();
                pathVariables.put("{id}", matcher.group("id"));
                result.setPathVariables(pathVariables);
            }
            result.setBody(getRequestBody());
            return result;

        }else throw new IllegalArgumentException("Unsupported url: "+request.getRequestURI());

    }

    private String getRequestBody() throws IOException {
        BufferedReader requestBodyReader = request.getReader();
        return requestBodyReader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    private String removeTrailingSlash(String url){
        if (url.endsWith("/")){
            return url.substring(0,url.length()-1);
        }
        return url;
    }

}
