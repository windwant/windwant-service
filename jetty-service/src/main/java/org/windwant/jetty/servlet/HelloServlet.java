package org.windwant.jetty.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * HelloServlet
 */
public class HelloServlet extends HttpServlet {
    public HelloServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println("<h1>" + (name == null?"random:":name) + ":" + Math.random()*1000 + "</h1>");
        resp.getWriter().println("session=" + req.getSession(true).getId());
    }
}
