package com.example.backend.demo.errors;

import java.io.Serializable;

public class ErrorInfo implements Serializable {
    private final String error;

    public ErrorInfo(String message) {
        this.error = message;
    }

    public String getError() {
        return error;
    }
}
