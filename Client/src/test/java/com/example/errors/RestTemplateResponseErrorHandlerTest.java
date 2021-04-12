package com.example.errors;

import com.example.app.errors.RestTemplateResponseErrorHandler;
import com.example.app.errors.ServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class RestTemplateResponseErrorHandlerTest {

    @Mock
    private ClientHttpResponse response;

    private RestTemplateResponseErrorHandler handler;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        handler = new RestTemplateResponseErrorHandler();
    }

    @Test
    public void testProperExceptionFormat() throws IOException {
        when(response.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        String errorInfoJson = "{\"error\":\"Test error\"}";
        InputStream errorInputStream = new ByteArrayInputStream(errorInfoJson.getBytes());
        when(response.getBody()).thenReturn(errorInputStream);
        Throwable thrownException = assertThrows(ServerException.class, ()->handler.handleError(response));
        assertEquals(thrownException.getMessage(), "Test error");
    }
}
