package source;

import chat.WebSocketChatServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;
import servlets.SignInServlet;
import servlets.SignOutServlet;
import servlets.SignUpServlet;

public class Source {
    public static void main(String[] args) throws Exception {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

        contextHandler.addServlet(new ServletHolder(new SignUpServlet()), "/signup");
        contextHandler.addServlet(new ServletHolder(new SignInServlet()), "/signin");
        contextHandler.addServlet(new ServletHolder(new SignOutServlet()), "/sign-out");
        contextHandler.addServlet(new ServletHolder(new WebSocketChatServlet()), "/chat");

        JettyWebSocketServletContainerInitializer.configure(contextHandler, null);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("html");

//        HandlerList handlers = new HandlerList();
//        handlers.setHandlers(new Handler[]{ resourceHandler, contextHandler });

        /*One more way*/
        HandlerList handlers = new HandlerList(resourceHandler, contextHandler);

        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
