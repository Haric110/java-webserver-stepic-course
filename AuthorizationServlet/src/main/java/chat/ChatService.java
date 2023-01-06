package chat;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatService {
    private final Set<ChatWebSocket> webSockets;

    public ChatService() {
        webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    public void sendMessage(String message) {
        for (ChatWebSocket user : webSockets) user.sendString(message);
    }

    public void add(ChatWebSocket chatWebSocket) {
        this.webSockets.add(chatWebSocket);
    }

    public void remove(ChatWebSocket chatWebSocket) {
        this.webSockets.remove(chatWebSocket);
    }
}
