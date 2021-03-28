package com.example.rest_entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

public class RequestEntityProvider {

    public RequestEntity createRequestEntity(BufferedReader reader, Map<String, String> pathVariables) {
        RequestEntity result = new RequestEntity();
        result.setBody(getRequestBody(reader));
        result.setPathVariables(pathVariables);
        return result;
    }

    private String getRequestBody(BufferedReader reader) {
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

}
