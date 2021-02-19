package com.example.backend.constants;

public enum HttpMethod {
    DELETE("DELETE"),
    POST("POST"),
    GET("GET"),
    PUT("PUT"),
    HEAD("HEAD"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE");

    private final String value;

    HttpMethod(String value) {
        this.value = value;
    }

    public String value(){return value;}

}
