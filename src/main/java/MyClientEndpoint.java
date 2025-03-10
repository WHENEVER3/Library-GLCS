import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;

@ClientEndpoint
public class MyClientEndpoint {
    @OnMessage
    public void onMessage(String message) {
        System.out.println("업데이트 수신: " + message);
        // 여기서 SwingUtilities.invokeLater() 등을 사용하여 GUI를 갱신할 수 있습니다.
        // 예: SwingUtilities.invokeLater(() -> BorrowHistoryGUI.refreshData());
    }
}
