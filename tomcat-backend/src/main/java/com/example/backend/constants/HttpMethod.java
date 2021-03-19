package com.example.backend.constants;

public enum HttpMethod {
    // TODO You don't need the constructor, or the value field and method. You can just use toString
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
