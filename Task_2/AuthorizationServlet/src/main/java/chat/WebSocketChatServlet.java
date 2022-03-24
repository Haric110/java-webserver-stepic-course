package chat;

import jakarta.servlet.annotation.WebServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

@WebServlet(name = "WebSocketServlet", urlPatterns = {"/chat"})
public class WebSocketChatServlet extends JettyWebSocketServlet {
    private static final int LOGOUT_TIMEOUT_MINUTES = 10;

    @Override
    protected void configure(@NotNull JettyWebSocketServletFactory factory) {
        factory.addMapping("/chat", ((req, resp) -> this));
        factory.setIdleTimeout(Duration.ofMinutes(LOGOUT_TIMEOUT_MINUTES));
    }
}
