package com.example.backend.servlets;

import com.example.constants.HttpMethod;
import com.example.constants.HttpStatus;
import com.example.backend.testcontrollers.EntityNotFoundController;
import com.example.backend.testcontrollers.InvalidBodyController;
import com.example.backend.testcontrollers.NoGetMethodController;
import com.example.servlets.DispatcherServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DispatcherServletTest {

    @Mock
    public HttpServletRequest request;

    @Mock
    public HttpServletResponse response;

    @BeforeEach
    public void setup() throws IOException {
        MockitoAnnotations.openMocks(this);
        setupTest();
    }

    private void setupTest() throws IOException {
        BufferedReader reader = Mockito.mock(BufferedReader.class);
        PrintWriter writer = Mockito.mock(PrintWriter.class);
        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
    }

    @ParameterizedTest
    @EnumSource(value = HttpMethod.class, names = {"GET", "DELETE"})
    public void recordNotFoundTest(HttpMethod method) throws IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        servlet.registerController(EntityNotFoundController.class);
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/notFoundController/10");
        when(request.getContextPath()).thenReturn("/tomcat_backend_war_exploded");
        when(request.getMethod()).thenReturn(method.name());
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void invalidRequestBodyTest() throws IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        servlet.registerController(InvalidBodyController.class);
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/invalidBodyController");
        when(request.getContextPath()).thenReturn("/tomcat_backend_war_exploded");
        when(request.getMethod()).thenReturn(HttpMethod.POST.name());
        servlet.doPost(request,response);
        verify(response).setStatus(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void methodNotImplementedTest() throws IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        servlet.registerController(NoGetMethodController.class);
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/noGetMethodController");
        when(request.getContextPath()).thenReturn("/tomcat_backend_war_exploded");
        when(request.getMethod()).thenReturn(HttpMethod.GET.name());
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
    }

    @ParameterizedTest
    @EnumSource(value = HttpMethod.class, names = {"GET", "DELETE"})
    public void invalidIdTest(HttpMethod method) throws IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/movies/invalidId");
        when(request.getMethod()).thenReturn(method.name());
        when(request.getContextPath()).thenReturn("/tomcat_backend_war_exploded");
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.BAD_REQUEST.value());
        verify(response.getWriter()).println("\"For input string: \\\"invalidId\\\": Should be a number.\"");
    }

    @ParameterizedTest
    @EnumSource(value = HttpMethod.class, names = {"GET", "POST", "DELETE"})
    public void invalidEntityTest(HttpMethod method) throws IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/humans");
        when(request.getContextPath()).thenReturn("/tomcat_backend_war_exploded");
        when(request.getMethod()).thenReturn(method.name());
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.NOT_FOUND.value());
    }

    @ParameterizedTest
    @EnumSource(value = HttpMethod.class, names = {"GET", "POST", "DELETE"})
    public void noEntityInUrlTest(HttpMethod method) throws IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/");
        when(request.getContextPath()).thenReturn("/tomcat_backend_war_exploded");
        when(request.getMethod()).thenReturn(method.name());
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void doPostWithIdTest() throws IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/movies/10");
        when(request.getContextPath()).thenReturn("/tomcat_backend_war_exploded");
        when(request.getMethod()).thenReturn(HttpMethod.POST.name());
        servlet.doPost(request,response);
        verify(response).setStatus(HttpStatus.NOT_FOUND.value());
    }

    @ParameterizedTest
    @EnumSource(value = HttpMethod.class, names = {"GET", "POST", "DELETE"})
    public void requestWithTooManyVariablesTest(HttpMethod method) throws IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/movies/test/test/test/test");
        when(request.getContextPath()).thenReturn("/tomcat_backend_war_exploded");
        when(request.getMethod()).thenReturn(method.name());
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.NOT_FOUND.value());
    }
}
