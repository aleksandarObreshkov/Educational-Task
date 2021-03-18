package com.example.backend.errors;

import java.io.Serializable;

// TODO You've implemented Serializable, but you haven't added a serialVersionUid constant to the class.
public class ErrorInfo implements Serializable {
    private final String error;

    public ErrorInfo(String message) {
        this.error = message;
    }

    public String getError() {
        return error;
    }
}
