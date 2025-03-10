import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/bookUpdates")
public class BookUpdateEndpoint {
    // 연결된 세션들을 동기화된 Set으로 관리
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("클라이언트 연결됨: " + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("클라이언트 연결 종료: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("서버 수신 메시지: " + message);
        // 받은 메시지를 모든 클라이언트에 브로드캐스트
        broadcast(message);
    }

    public static void broadcast(String message) {
        synchronized (sessions) {
            for (Session session : sessions) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
