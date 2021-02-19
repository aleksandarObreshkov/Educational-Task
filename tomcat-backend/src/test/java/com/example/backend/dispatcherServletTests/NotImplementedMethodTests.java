package com.example.backend.dispatcherServletTests;

import com.example.backend.constants.HttpMethod;
import com.example.backend.constants.HttpStatus;
import com.example.backend.servlets.DispatcherServlet;
import com.example.backend.testControllers.NoGetMethodController;
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

public class NotImplementedMethodTests {

    @Mock
    public HttpServletRequest request;

    @Mock
    public HttpServletResponse response;

    @BeforeEach
    public void setupVariables() throws IOException {
        MockitoAnnotations.openMocks(this);
        setupTest();
    }

    public void setupTest() throws IOException {
        ServletOutputStream stream = Mockito.mock(ServletOutputStream.class);
        BufferedReader reader = Mockito.mock(BufferedReader.class);
        when(response.getOutputStream()).thenReturn(stream);
        when(request.getReader()).thenReturn(reader);
    }

    @Test
    public void putMethodNotImplemented() throws IOException, ServletException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        setupTest();
        servlet.registerController(NoGetMethodController.class);
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/noGetMethodController");
        when(request.getMethod()).thenReturn(HttpMethod.GET.value());
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
    }
}
