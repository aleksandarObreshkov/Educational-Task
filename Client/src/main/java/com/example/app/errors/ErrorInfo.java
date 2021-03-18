package com.example.app.errors;

import java.io.Serializable;

// TODO This is a duplication of the class from the backend module. You could extract this in the models module and
// reuse it from there. This would ensure that a developer won't change the response of the backend without also
// adapting the client to that change.
public class ErrorInfo implements Serializable {

    private String error;

    public ErrorInfo() {
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

}
