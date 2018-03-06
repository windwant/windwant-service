package org.windwant.jetty;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.windwant.jetty.servlet.HelloServlet;
import org.windwant.jetty.websocket.HelloWebSocket;

/**
 * ServletContextServer
 */
public class ServletContextServer {
    public static void main(String[] args) {
        Server server = new Server();

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.setConnectors(new Connector[]{connector});


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/jetty");
        context.addServlet(new ServletHolder(new HelloWebSocket()), "/hello");

        context.addServlet(new ServletHolder(new HelloServlet()), "/helloworld");

        HandlerCollection handlerCollection = new HandlerCollection();
        handlerCollection.setHandlers(new Handler[]{context});

        server.setHandler(handlerCollection);


        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
