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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class entityNotFoundTests {
    @Mock
    public HttpServletRequest request;

    @Mock
    public HttpServletResponse response;

    @BeforeEach
    public void setup() throws IOException {
        MockitoAnnotations.openMocks(this);
        setupTest();
    }

    @Test
    public void deleteRecordNotFound() throws IOException, ServletException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        servlet.registerController(EntityNotFoundController.class);
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/notFoundController/10");
        when(request.getMethod()).thenReturn(HttpMethod.DELETE.value());
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void getRecordNotFound() throws IOException, ServletException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        servlet.registerController(EntityNotFoundController.class);
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/notFoundController/10");
        when(request.getMethod()).thenReturn(HttpMethod.GET.value());
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.NOT_FOUND.value());
    }

    private void setupTest() throws IOException {
        ServletOutputStream stream = Mockito.mock(ServletOutputStream.class);
        BufferedReader reader = Mockito.mock(BufferedReader.class);
        when(request.getReader()).thenReturn(reader);
        when(response.getOutputStream()).thenReturn(stream);
    }
}
