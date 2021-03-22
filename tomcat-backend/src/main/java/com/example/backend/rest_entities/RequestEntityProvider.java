package com.example.backend.rest_entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

public class RequestEntityProvider {

    private final BufferedReader requestReader;

    // TODO The body should be a parameter of the createRequestEntity method, as it has the same semantics as the path
    // variable values.
    public RequestEntityProvider(BufferedReader requestReader) {
        this.requestReader = requestReader;
    }

    // TODO This method doesn't need its first parameter. Furthermore, the path variable values are already built in the
    // map, so most of this class isn't needed as well.
    public RequestEntity createRequestEntity(List<String> pathVariableKeys, Map<String, String> pathVariableValues)
            throws IOException {
        RequestEntity result = new RequestEntity();
        result.setBody(getRequestBody());
        result.setPathVariables(createPathVariablesMap(pathVariableKeys, pathVariableValues));
        return result;
    }

    private String getRequestBody() throws IOException {
        return requestReader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    private Map<String, String> createPathVariablesMap(List<String> pathVariableKeys,
                                                       Map<String, String> pathVariableValues) {

        Map<String, String> pathVariables = new HashMap<>();
        for (String currentKey : pathVariableKeys) {
            pathVariables.put(currentKey, pathVariableValues.get(currentKey));
        }
        return pathVariables;
    }

}
