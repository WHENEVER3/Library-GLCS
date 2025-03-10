import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import LJY.BookList;

public class LogViewGUI extends JFrame {
    public LogViewGUI(BookList bookList) {
        setTitle("대출/반납 기록");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {"책 제목", "바코드", "행동", "학년", "사용자", "시간"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

        // 셀 가운데 정렬을 위한 렌더러 생성 및 적용
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        List<BookList.LogEntry> logs = bookList.getLogEntries();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (BookList.LogEntry log : logs) {
            tableModel.addRow(new Object[] {
                    log.title,
                    log.barcode,
                    log.action,
                    log.grade,
                    log.user,
                    sdf.format(new Date(log.timestamp))
            });
        }

        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
