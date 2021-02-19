package com.example.backend.dispatcherServletTests.urlVaidationTests;


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
import java.io.IOException;
import static org.mockito.Mockito.*;

public class URLValidationDeleteTests {

    @Mock
    public HttpServletRequest request;
    @Mock
    public HttpServletResponse response;

    @BeforeEach
    public void variableSetup(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testDoDeleteWithInvalidUrl() throws ServletException, IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/movies////");
        when(request.getMethod()).thenReturn(HttpMethod.DELETE.value());
        ServletOutputStream stream = Mockito.mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(stream);
        servlet.doGet(request, response);
        verify(response).setStatus(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    public void testDoDeleteWithInvalidId() throws ServletException, IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/movies/invalidId");
        when(request.getMethod()).thenReturn(HttpMethod.DELETE.value());
        ServletOutputStream stream = Mockito.mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(stream);
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testDoDeleteWithInvalidEntity() throws ServletException, IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/humans");
        when(request.getMethod()).thenReturn(HttpMethod.DELETE.value());
        ServletOutputStream stream = Mockito.mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(stream);
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testDoDeleteWithTooManyVariables() throws ServletException, IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/movies/test/test/test/test");
        when(request.getMethod()).thenReturn(HttpMethod.DELETE.value());
        ServletOutputStream stream = Mockito.mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(stream);
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testDoDeleteWithoutEntity() throws ServletException, IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/");
        when(request.getMethod()).thenReturn(HttpMethod.DELETE.value());
        ServletOutputStream stream = Mockito.mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(stream);
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.NOT_FOUND.value());
    }
}

