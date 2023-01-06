package chat;

import jakarta.servlet.annotation.WebServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"})
public class WebSocketChatServlet extends JettyWebSocketServlet {
    private static final int LOGOUT_TIMEOUT_MINUTES = 10;
    private final ChatService chatService;

    public WebSocketChatServlet() {
        chatService = new ChatService();
    }

    @Override
    protected void configure(@NotNull JettyWebSocketServletFactory factory) {
        Class<?> obj = WebSocketChatServlet.class;
        if (obj.isAnnotationPresent(WebServlet.class)){
            WebServlet annotation = obj.getAnnotation(WebServlet.class);
            String pathSpec = annotation.urlPatterns()[0];

            factory.setIdleTimeout(Duration.ofMinutes(LOGOUT_TIMEOUT_MINUTES));
            factory.addMapping(pathSpec, ((req, resp) -> new ChatWebSocket(chatService)));
        }
    }
}
