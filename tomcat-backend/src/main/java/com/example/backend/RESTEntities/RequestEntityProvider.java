package com.example.backend.RESTEntities;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

public class RequestEntityProvider {

    private final HttpServletRequest request;

    public RequestEntityProvider(HttpServletRequest request) {
        this.request = request;
    }

    public RequestEntity createRequestEntity(List<String> pathVariableKeys, Map<String, String> pathVariableValues) throws IOException {
        RequestEntity result = new RequestEntity();
        result.setBody(getRequestBody());
        result.setPathVariables(createPathVariablesMap(pathVariableKeys, pathVariableValues));
        return result;
    }

    private String getRequestBody() throws IOException {
        BufferedReader requestBodyReader = request.getReader();
        return requestBodyReader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    private Map<String, String> createPathVariablesMap(List<String> pathVariableKeys, Map<String, String> pathVariableValues){

        Map<String, String> pathVariables = new HashMap<>();
        for (String currentKey : pathVariableKeys) {
            pathVariables.put(currentKey, pathVariableValues.get(currentKey));
        }
        return pathVariables;
    }

}
