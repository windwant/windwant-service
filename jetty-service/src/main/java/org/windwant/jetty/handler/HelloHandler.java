package org.windwant.jetty.handler;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * HelloHandler
 */
public class HelloHandler extends AbstractHandler {
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        String name = httpServletRequest.getParameter("name");
        httpServletResponse.setContentType("text/html");
        request.setHandled(true);
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.getWriter().println("<h1>" + (name == null?"random:":name) + ":" + Math.random()*1000 + "</h1>");
        httpServletResponse.getWriter().println("session=" + httpServletRequest.getSession(true).getId());
    }

    public static void main(String[] args) {
        Server server = new Server(8088);
        HelloHandler helloHandler = new HelloHandler();
        server.setHandler(helloHandler);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
