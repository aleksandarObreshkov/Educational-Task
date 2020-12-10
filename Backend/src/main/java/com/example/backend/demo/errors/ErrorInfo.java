package com.example.backend.demo.errors;

public class ErrorInfo {
    private String url;
    private String ex;

    public ErrorInfo(String url, String ex) {
        this.url = url;
        this.ex = ex;
    }

    public String getUrl() {
        return url;
    }

    public String getEx() {
        return ex;
    }
}
