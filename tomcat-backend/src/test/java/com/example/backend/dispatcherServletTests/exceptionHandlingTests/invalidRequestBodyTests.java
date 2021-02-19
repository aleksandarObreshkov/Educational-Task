package com.example.backend.dispatcherServletTests.exceptionHandlingTests;

import com.example.backend.testControllers.EntityNotFoundController;
import com.example.backend.constants.HttpMethod;
import com.example.backend.constants.HttpStatus;
import com.example.backend.servlets.DispatcherServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class invalidRequestBodyTests {

    @Mock
    public HttpServletRequest request;

    @Mock
    public HttpServletResponse response;

    @BeforeEach
    public void setupVariables() throws IOException {
        MockitoAnnotations.openMocks(this);
        setupTest();
    }

    private void setupTest() throws IOException {
        ServletOutputStream stream = Mockito.mock(ServletOutputStream.class);
        BufferedReader reader = Mockito.mock(BufferedReader.class);
        when(request.getReader()).thenReturn(reader);
        when(response.getOutputStream()).thenReturn(stream);
    }

    @Test
    public void invalidRequestBody() throws ServletException, IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        servlet.registerController(EntityNotFoundController.class);
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/invalidBodyController");
        when(request.getMethod()).thenReturn(HttpMethod.POST.value());
        servlet.doPost(request,response);
        verify(response).setStatus(HttpStatus.BAD_REQUEST.value());
    }
}
