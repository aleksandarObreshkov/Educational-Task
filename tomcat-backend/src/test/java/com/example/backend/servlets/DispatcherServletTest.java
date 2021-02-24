package com.example.backend.servlets;

import com.example.backend.constants.HttpMethod;
import com.example.backend.constants.HttpStatus;
import com.example.backend.testcontrollers.EntityNotFoundController;
import com.example.backend.testcontrollers.InvalidBodyController;
import com.example.backend.testcontrollers.NoGetMethodController;
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
    public void recordNotFoundTest(HttpMethod method) throws IOException, ServletException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        servlet.registerController(EntityNotFoundController.class);
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/notFoundController/10");
        when(request.getMethod()).thenReturn(method.value());
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void invalidRequestBodyTest() throws ServletException, IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        servlet.registerController(InvalidBodyController.class);
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/invalidBodyController");
        when(request.getMethod()).thenReturn(HttpMethod.POST.value());
        servlet.doPost(request,response);
        verify(response).setStatus(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void methodNotImplementedTest() throws IOException, ServletException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        servlet.registerController(NoGetMethodController.class);
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/noGetMethodController");
        when(request.getMethod()).thenReturn(HttpMethod.GET.value());
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
    }

    @ParameterizedTest
    @EnumSource(value = HttpMethod.class, names = {"GET", "DELETE"})
    public void invalidIdTest(HttpMethod method) throws ServletException, IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/movies/invalidId");
        when(request.getMethod()).thenReturn(method.value());
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.BAD_REQUEST.value());
        verify(response.getWriter()).println("\"For input string: \\\"invalidId\\\": Invalid id\"");
    }

    @ParameterizedTest
    @EnumSource(value = HttpMethod.class, names = {"GET", "POST", "DELETE"})
    public void invalidEntityTest(HttpMethod method) throws ServletException, IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/humans");
        when(request.getMethod()).thenReturn(method.value());
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.NOT_FOUND.value());
    }

    @ParameterizedTest
    @EnumSource(value = HttpMethod.class, names = {"GET", "POST", "DELETE"})
    public void noEntityInUrlTest(HttpMethod method) throws ServletException, IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/");
        when(request.getMethod()).thenReturn(method.value());
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void doPostWithIdTest() throws ServletException, IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/movies/10");
        when(request.getMethod()).thenReturn(HttpMethod.POST.value());
        servlet.doPost(request,response);
        verify(response).setStatus(HttpStatus.NOT_FOUND.value());
    }

    @ParameterizedTest
    @EnumSource(value = HttpMethod.class, names = {"GET", "POST", "DELETE"})
    public void requestWithTooManyVariablesTest(HttpMethod method) throws ServletException, IOException {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.init();
        when(request.getRequestURI()).thenReturn("/tomcat_backend_war_exploded/movies/test/test/test/test");
        when(request.getMethod()).thenReturn(method.value());
        servlet.doGet(request,response);
        verify(response).setStatus(HttpStatus.NOT_FOUND.value());
    }
}
