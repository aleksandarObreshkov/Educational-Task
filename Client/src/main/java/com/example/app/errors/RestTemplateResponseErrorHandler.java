package com.example.app.errors;

import com.example.errors.ErrorInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().series() == CLIENT_ERROR || response.getStatusCode().series() == SERVER_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        // Since template.delete() doesn't return void, I have added the 400 series as errors in order to be
        // able to catch the Not_Found case
        if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new InvalidInputException("Entity with the specified id was not found.");
        }
        ErrorInfo errorInfo = new ObjectMapper().readValue(response.getBody(), ErrorInfo.class);
        throw new ServerException(errorInfo.getError());
    }

}
