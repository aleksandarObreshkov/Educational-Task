package com.example.backend;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class CharacterController extends HttpServlet{

    private final EntityService service = new EntityService();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            out.println(service.testMethod());
            out.println("Character controller works!");
        } finally {
            out.close();
        }
    }
    
}
