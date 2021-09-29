package com.webscoket.endpoint;

import com.webscoket.api.BulletScreen;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint("/websocket")
public class WebSocket {

    private static CopyOnWriteArraySet<WebSocket> websocketPools = new CopyOnWriteArraySet<WebSocket>();

    private Session session;

    boolean index = false;

    @OnOpen
    public void onOpen() throws IOException {
        this.session = session;
        websocketPools.add(this);
        index = true;
        OnMessage("Welcome to DEYAO danmu ^_^", this.session);
    }

    @OnClose
    public void OnClose() {
        System.out.println("OnClose");
    }

    @OnMessage
    public void OnMessage(String message, Session session) throws IOException {
        for (WebSocket item : websocketPools) {
            item.send(message);
        }
    }

    public void send(String message) {
        this.session.getAsyncRemote().sendText(message);
    }

}
