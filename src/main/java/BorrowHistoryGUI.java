import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import LJY.BookList;

public class BorrowHistoryGUI extends JFrame {
    private final BookList bookList;
    private final String username;
    private DefaultTableModel tableModel;
    private JTable table;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private BookUpdateClient updateClient;

    public BorrowHistoryGUI(BookList bookList, String username) {
        this.bookList = bookList;
        this.username = username;

        setTitle("대출 내역 조회");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        // WebSocket 클라이언트 연결
        updateClient = new BookUpdateClient();
        updateClient.connect("ws://localhost:8025/bookUpdates");

        // 컬럼 구성
        String[] columns = {"책 제목", "위치", "대출 상태", "대출 시각", "대출자", "학년", "바코드"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);

        // 테이블 셀의 텍스트를 가운데 정렬
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // "바코드" 컬럼 숨김
        table.removeColumn(table.getColumnModel().getColumn(6));
        table.getColumnModel().getColumn(2).setCellRenderer(new StatusCellRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        if ("glcs".equals(username)) {
            JButton bulkImportButton = new JButton("대량 추가");
            bulkImportButton.addActionListener(e -> new BulkImportGUI(bookList, this));
            bottomPanel.add(bulkImportButton);

            JButton addBookButton = new JButton("책 추가하기");
            addBookButton.addActionListener(e -> new AddBookGUI(bookList, this));
            bottomPanel.add(addBookButton);

            JButton viewLogButton = new JButton("로그 보기");
            viewLogButton.addActionListener(e -> new LogViewGUI(bookList));
            bottomPanel.add(viewLogButton);

            JButton deleteBookButton = new JButton("책 삭제");
            deleteBookButton.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "삭제할 책을 선택하세요.");
                    return;
                }
                String barcode = (String) tableModel.getValueAt(table.convertRowIndexToModel(selectedRow), 6);
                int confirm = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?");
                if (confirm == JOptionPane.YES_OPTION) {
                    if (bookList.deleteBook(barcode)) {
                        JOptionPane.showMessageDialog(this, "책 삭제 성공.");
                        loadBookList();
                    } else {
                        JOptionPane.showMessageDialog(this, "삭제 실패.");
                    }
                }
            });
            bottomPanel.add(deleteBookButton);

            JButton exitButton = new JButton("종료");
            exitButton.addActionListener(e -> {
                bookList.saveData();
                System.exit(0);
            });
            bottomPanel.add(exitButton);
        }
        add(bottomPanel, BorderLayout.SOUTH);

        loadBookList();
        setVisible(true);
    }

    public void loadBookList() {
        tableModel.setRowCount(0);
        for (BookList.Book book : bookList.getSortedBooks()) {
            if (!"glcs".equals(username) && !book.isBorrowed) continue;
            String borrowTime = book.isBorrowed ? sdf.format(new Date(book.borrowTime)) : "-";
            String borrower = book.isBorrowed ? book.borrowedBy : "-";
            String grade = book.isBorrowed ? book.grade : "-";

            tableModel.addRow(new Object[]{
                    book.title,
                    book.location,
                    book.isBorrowed ? "대출 중" : "대출 가능",
                    borrowTime,
                    borrower,
                    grade,
                    book.barcode
            });
        }
    }
}
