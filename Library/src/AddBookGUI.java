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
        setSize(300, 200);
        setLayout(new GridLayout(3, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("책 제목:"));
        JTextField titleField = new JTextField();
        add(titleField);

        add(new JLabel("위치:"));
        JTextField locationField = new JTextField();
        add(locationField);

        JButton addButton = new JButton("추가");
        add(addButton);
        JButton cancelButton = new JButton("취소");
        add(cancelButton);

        addButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String location = locationField.getText().trim();
            if (title.isEmpty() || location.isEmpty()) {
                JOptionPane.showMessageDialog(this, "모든 항목을 입력하세요.");
                return;
            }
            // 자동으로 바코드 생성 (현재 시간 사용)
            String barcode = String.valueOf(System.currentTimeMillis());
            boolean success = bookList.addBook(title, location, barcode);
            if (success) {
                JOptionPane.showMessageDialog(this, "책이 추가되었습니다.");
                parent.loadData(); // 목록 즉시 갱신
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "책 추가 실패: 이미 동일한 책이 존재합니다.");
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setLocationRelativeTo(null);
        setVisible(true);
    }

}
