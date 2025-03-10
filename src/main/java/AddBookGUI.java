import javax.swing.*;
import java.awt.*;
import LJY.BookList;

public class AddBookGUI extends JFrame {
    private final BookList bookList;
    private final BorrowHistoryGUI parent;

    public AddBookGUI(BookList bookList, BorrowHistoryGUI parent) {
        this.bookList = bookList;
        this.parent = parent;

        setTitle("책 추가하기");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        // 라벨과 텍스트 필드를 생성하여 추가
        JTextField titleField = createLabeledField("책 제목:");
        JTextField locationField = createLabeledField("위치:");
        JTextField genreField = createLabeledField("장르:");

        // 버튼 추가
        JButton addButton = new JButton("추가");
        JButton cancelButton = new JButton("취소");
        add(addButton);
        add(cancelButton);

        // 액션 리스너 설정
        addButton.addActionListener(e -> addBookAction(titleField, locationField, genreField));
        cancelButton.addActionListener(e -> dispose());

        // 창 위치 및 크기 조정
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setSize(300, 250);
        setVisible(true);
    }

    // 라벨과 텍스트필드를 한 번에 추가하는 헬퍼 메서드
    private JTextField createLabeledField(String labelText) {
        add(new JLabel(labelText, JLabel.CENTER));
        JTextField textField = new JTextField();
        add(textField);
        return textField;
    }

    // 책 추가 버튼 클릭 시 처리하는 로직
    private void addBookAction(JTextField titleField, JTextField locationField, JTextField genreField) {
        String title = titleField.getText().trim();
        String location = locationField.getText().trim();
        String genre = genreField.getText().trim();

        if (title.isEmpty() || location.isEmpty() || genre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "모든 항목(책 제목, 위치, 장르)을 입력하세요.");
            return;
        }

        // 현재 시간을 이용해 바코드 자동 생성
        String barcode = String.valueOf(System.currentTimeMillis());
        boolean success = bookList.addBook(title, location, barcode);
        if (success) {
            // 책 객체에 장르 설정 (만약 addBook 메서드 내에서 처리되지 않는 경우)
            BookList.Book book = bookList.searchByBarcode(barcode);
            if (book != null) {
                book.genre = genre;
            }
            JOptionPane.showMessageDialog(this, "책이 추가되었습니다.");
            parent.loadBookList(); // 목록 즉시 갱신
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "책 추가 실패: 이미 동일한 책이 존재합니다.");
        }
    }
}