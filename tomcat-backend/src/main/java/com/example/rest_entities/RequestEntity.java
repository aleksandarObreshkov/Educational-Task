// TODO Adhere to package naming conventions.
package com.example.rest_entities;

import java.util.HashMap;
import java.util.Map;

public class RequestEntity {

    private String body;
    private Map<String, String> pathVariables;

    public RequestEntity() {
        // TODO This initialization can be moved to where the field is declared.
        pathVariables=new HashMap<>();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getPathVariables() {
        return pathVariables;
    }

    public void setPathVariables(Map<String, String> pathVariables) {
        this.pathVariables = pathVariables;
    }

}
