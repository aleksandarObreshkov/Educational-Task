package com.example.backend.constants;

public enum HttpStatus {
    OK(200, "OK"),
    CREATED(201, "Created"),
    NO_CONTENT(204, "No content"),

    BAD_REQUEST(400, "Bad request"),
    NOT_FOUND(404, "Not found"),
    METHOD_NOT_ALLOWED(405, "Method not allowed"),

    INTERNAL_SERVER_ERROR(500, "Internal server error");

    private final int value;
    private final String textRepresentation;

    HttpStatus(int statusCode, String textRepresentation) {
        this.value = statusCode;
        this.textRepresentation = textRepresentation;
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return value+" "+textRepresentation;
    }
}
