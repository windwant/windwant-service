package org.windwant.jetty.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;

import java.io.IOException;

/**
 * MyEchoSocket
 */
public class MyEchoSocket implements WebSocketListener {

    private Session session;
    public void onWebSocketBinary(byte[] bytes, int i, int i1) {

    }

    public void onWebSocketText(String s) {
        if (session == null){
            return;
        }

        try{
            System.out.println("server received msg: " + s);
            session.getRemote().sendString("server response msg: " + s);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onWebSocketClose(int i, String s) {
        session = null;
    }

    public void onWebSocketConnect(Session session) {
        this.session = session;
    }

    public void onWebSocketError(Throwable throwable) {
        throwable.printStackTrace();
    }
}
