package com.example.backend.constants;

public enum HttpMethod {
    DELETE("delete"),
    POST("post"),
    GET("get");

    private final String value;

    HttpMethod(String value) {
        this.value = value;
    }

    public String value(){return value;}

}
