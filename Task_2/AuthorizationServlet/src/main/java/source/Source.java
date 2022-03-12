package source;

import dbService.DBService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.SignInServlet;
import servlets.SignOutServlet;
import servlets.SignUpServlet;

public class Source {
    public static void main(String[] args) throws Exception {
        SignUpServlet signUpServlet = new SignUpServlet();
        SignInServlet signInServlet = new SignInServlet();
        SignOutServlet signOutServlet = new SignOutServlet();

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(signUpServlet), "/signup");
        contextHandler.addServlet(new ServletHolder(signInServlet), "/signin");
        contextHandler.addServlet(new ServletHolder(signOutServlet), "/sign-out");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{ resourceHandler, contextHandler });

        DBService dbService = DBService.getInstance();

        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
