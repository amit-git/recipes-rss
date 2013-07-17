package com.netflix.recipes.rss.server;

import com.google.inject.servlet.GuiceFilter;
import com.netflix.karyon.server.guice.KaryonGuiceContextListener;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class PytheasJettyServer {

    private static final int PORT = 8989;

    public static void main(final String[] args) throws Exception {
        System.setProperty("archaius.deployment.applicationId","feeds-app");
        System.setProperty("archaius.deployment.environment","dev");

        Server server = new Server(PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addEventListener(new KaryonGuiceContextListener());

        context.addFilter(GuiceFilter.class, "/*", 1);
        context.addServlet(DefaultServlet.class, "/");

        server.setHandler(context);

        server.start();
    }


}
