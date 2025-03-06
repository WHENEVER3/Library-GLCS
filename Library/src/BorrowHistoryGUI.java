import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import LJY.BookList;

public class BorrowHistoryGUI extends JFrame {
    private final BookList bookList;
    private final String username;
    private DefaultTableModel tableModel;
    private JTable table;

    public BorrowHistoryGUI(BookList bookList, String username) {
        this.bookList = bookList;
        this.username = username;
        setTitle("대출 내역 조회");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 테이블 모델 생성: 책 제목, 위치, 대출 상태, 대출자, 학년, 대출 시간, 바코드(숨김)
        String[] columns = {"책 제목", "위치", "대출 상태", "대출자", "학년", "대출 시간", "바코드"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        // 숨김 처리: 마지막(7번째) 컬럼인 바코드는 모델에 남아 있으나 화면에서는 보이지 않음
        table.removeColumn(table.getColumnModel().getColumn(6));

        loadData();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 관리자 계정이면 하단에 "책 추가하기" 및 "책 삭제하기" 버튼 추가
        if ("glcs".equals(username)) {
            JButton addBookButton = new JButton("책 추가하기");
            addBookButton.addActionListener(e -> new AddBookGUI(bookList, this));

            JButton deleteBookButton = new JButton("책 삭제하기");
            deleteBookButton.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "삭제할 책을 선택하세요.");
                    return;
                }
                // 숨긴 바코드 컬럼은 모델의 인덱스 6에 있음
                String barcode = (String) tableModel.getValueAt(selectedRow, 6);
                int confirm = JOptionPane.showConfirmDialog(this, "정말 이 책을 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = bookList.deleteBook(barcode);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "책이 삭제되었습니다.");
                        loadData();  // 삭제 후 테이블 갱신: 삭제한 책이 목록에 보이지 않게 됩니다.
                    } else {
                        JOptionPane.showMessageDialog(this, "책 삭제 실패.");
                    }
                }
            });


            JPanel bottomPanel = new JPanel();
            bottomPanel.add(addBookButton);
            bottomPanel.add(deleteBookButton);
            add(bottomPanel, BorderLayout.SOUTH);
        }

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // loadData() 메서드: 관리자는 모든 책을, 일반 사용자는 대출 중인 책만 표시
    public void loadData() {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Map.Entry<String, BookList.Book> entry : bookList.getBooks().entrySet()) {
            BookList.Book book = entry.getValue();
            // 일반 사용자는 대출 중인 책만 표시, 관리자("admin")는 모든 책 표시
            if (!"glcs".equals(username) && !book.isBorrowed) continue;
            String borrowTimeStr = "";
            if (book.isBorrowed) {
                borrowTimeStr = sdf.format(new Date(book.borrowTime));
            }
            Object[] row = {
                    book.title,
                    book.location,
                    book.isBorrowed ? "대출 중" : "대출 가능",
                    book.borrowedBy,
                    book.grade,
                    borrowTimeStr,
                    book.barcode   // 숨긴 컬럼
            };
            tableModel.addRow(row);
        }
    }
}
