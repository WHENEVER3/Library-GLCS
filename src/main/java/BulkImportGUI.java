import LJY.BookList;
import javax.swing.*;
import java.io.File;

public class BulkImportGUI extends JFrame {
    public BulkImportGUI(BookList bookList, BorrowHistoryGUI parent) {
        setTitle("대량 책 등록");
        setSize(400, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JButton selectFileButton = new JButton("CSV 파일 선택");
        add(selectFileButton);
        selectFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                bookList.importBooksFromCSV(file.getAbsolutePath());
                parent.loadBookList(); // 대출 내역 목록 갱신
                dispose();
            }
        });
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
