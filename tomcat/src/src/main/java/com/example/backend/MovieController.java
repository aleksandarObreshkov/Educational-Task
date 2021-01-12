package com.example.backend;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class MovieController extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        EntityService service = new EntityService();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            out.println(service.getById(51L, Movie.class));
        } catch (Exception e) {
            out.println(e.getMessage());
            out.println("Movie was null");
        }finally
        {
            out.close();
        }
    }
    
}
