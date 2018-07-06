package org.windwant.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.windwant.jetty.config.WebMvcConfig;

/**
 * jetty 加载spring
 * test：http://localhost:8080/jetty/hello
 */
public class JettyServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath("/jetty");

        //spring 配置
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(WebMvcConfig.class);
        ctx.registerShutdownHook();

        handler.addServlet(new ServletHolder(new DispatcherServlet(ctx)), "/");
        handler.addEventListener(new ContextLoaderListener(ctx));

        server.setHandler(handler);

        server.start();
        server.join();
    }
}
