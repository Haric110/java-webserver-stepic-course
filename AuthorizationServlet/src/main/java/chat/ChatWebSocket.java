package chat;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@SuppressWarnings("UnusedDeclaration")
@WebSocket
public class ChatWebSocket {
    private final ChatService chatService;
    private Session session;

    public ChatWebSocket(ChatService chatService) {
        this.chatService = chatService;
    }

    @OnWebSocketConnect
    public void onOpen(final @NotNull Session session) {
        this.chatService.add(this);
        this.session = session;
        System.out.println("new session: " + session.getRemote().getRemoteAddress().toString());
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        this.chatService.remove(this);
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        this.chatService.sendMessage(data);
    }

    public void sendString(String data) {
        try {
            session.getRemote().sendString(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
