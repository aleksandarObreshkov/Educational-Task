package com.example.app.errors;

import java.io.Serializable;

public class ErrorInfo implements Serializable {

    private String error;

    public ErrorInfo() { }

    public void setError(String error){
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
