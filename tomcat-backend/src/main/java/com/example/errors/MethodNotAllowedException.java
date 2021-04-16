package com.example.errors;

public class MethodNotAllowedException extends Throwable {

    // TODO serialVersionUID instead of serialVersionUid
    private static final Long serialVersionUid = 1L;

    private final String message;

    public MethodNotAllowedException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
