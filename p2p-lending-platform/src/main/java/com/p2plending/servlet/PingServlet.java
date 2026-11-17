package com.p2plending.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * A plain HttpServlet (Module 1: Servlets) registered directly with the
 * embedded Tomcat container - no Spring MVC / @Controller involved here.
 * Demonstrates the raw Servlet lifecycle (init, service, doGet, destroy)
 * alongside the Spring MVC controllers used for the rest of the app.
 *
 * Visit: http://localhost:8080/ping
 */
public class PingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            out.println("P2P Lending Platform backend is running.");
            out.println("Servlet: " + this.getClass().getSimpleName());
            out.println("Server time: " + LocalDateTime.now());
            out.println("Session ID: " + request.getSession(true).getId());
        }
    }
}
