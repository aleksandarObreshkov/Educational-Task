package com.example.backend.constants;

public enum HttpStatus {
    OK(200, "OK"),
    NO_CONTENT(204, "No content"),

    BAD_REQUEST(400, "Bad request"),
    NOT_FOUND(404, "Not found"),

    INTERNAL_SERVER_ERROR(500, "Bad request");

    private final int value;
    private final String textRepresentation;

    private HttpStatus(int statusCode, String textRepresentation) {
        this.value = statusCode;
        this.textRepresentation = textRepresentation;
    }

    public int value() {
        return value;
    }

    public String getTextRepresentation() {
        return textRepresentation;
    }


    @Override
    public String toString() {
        return value+" "+textRepresentation;
    }
}
