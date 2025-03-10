import javax.swing.*;
import java.awt.*;
import LJY.BookList;

public class BookDetailGUI extends JFrame {
    private final BookList bookList;
    private final String barcode;
    private final GUI mainGUI;  // 메인 GUI의 참조

    public BookDetailGUI(BookList bookList, String barcode, GUI mainGUI) {
        this.bookList = bookList;
        this.barcode = barcode;
        this.mainGUI = mainGUI;  // 메인 GUI의 참조 저장

        // 대출 상태를 확인하기 위해 책 객체를 조회합니다.
        BookList.Book book = bookList.searchByBarcode(barcode);
        if (book == null) {
            JOptionPane.showMessageDialog(this, "📛 오류: 해당 바코드의 책을 찾을 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setTitle(book.title + " - 상세 정보");
        setSize(350, 300);
        setLayout(new GridLayout(6, 1, 10, 10));

        // 폰트 설정: 시인성 향상을 위해 글씨 크기 조정
        Font labelFont = new Font("SansSerif", Font.PLAIN, 16);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 16);

        JLabel titleLabel = new JLabel("📖 제목: " + book.title);
        titleLabel.setFont(labelFont);
        JLabel locationLabel = new JLabel("📍 위치: " + book.location);
        locationLabel.setFont(labelFont);
        JLabel statusLabel = new JLabel("📌 상태: " + (book.isBorrowed ? "대출 중" : "대출 가능"));
        statusLabel.setFont(labelFont);
        add(titleLabel);
        add(locationLabel);
        add(statusLabel);

        // 대출 및 반납 버튼 추가
        JButton borrowButton = new JButton("📕 대출");
        borrowButton.setFont(buttonFont);
        JButton returnButton = new JButton("📗 반납");
        returnButton.setFont(buttonFont);
        add(borrowButton);
        add(returnButton);

        // 대출 버튼 액션 처리
        borrowButton.addActionListener(e -> {
            if (book.isBorrowed) {
                // 이미 대출 중이면 바로 메시지 표시
                JOptionPane.showMessageDialog(this, "대출 실패: 이미 대출 중인 책입니다.");
            } else {
                // 대출 가능하면 학생 이름과 학년 입력받기
                String studentName = JOptionPane.showInputDialog(this, "학생 이름을 입력하세요:");
                String grade = JOptionPane.showInputDialog(this, "학년을 입력하세요:");
                if (studentName != null && !studentName.isEmpty() && grade != null && !grade.isEmpty()) {
                    boolean result = bookList.borrowBook(barcode, studentName, grade);
                    if (result) {
                        JOptionPane.showMessageDialog(this, "대출이 완료되었습니다.");
                        mainGUI.loadBookList(); // 메인 GUI 테이블 갱신
                        dispose(); // 작업 후 창 자동 닫힘
                    } else {
                        JOptionPane.showMessageDialog(this, "대출 실패: 이미 대출 중인 책입니다.");
                    }
                }
            }
        });

        // 반납 버튼 액션 처리
        returnButton.addActionListener(e -> {
            if (!book.isBorrowed) {
                JOptionPane.showMessageDialog(this, "반납 실패: 이미 반납된 책입니다.");
            } else {
                // 반납 시 대출 당시 입력했던 학생 이름과 학년을 확인하도록 입력받기
                String returnStudentName = JOptionPane.showInputDialog(this, "반납을 위해 학생 이름을 입력하세요:");
                String returnGrade = JOptionPane.showInputDialog(this, "반납을 위해 학년을 입력하세요:");
                if (returnStudentName == null || returnStudentName.isEmpty() ||
                        returnGrade == null || returnGrade.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "반납 취소: 학생 정보가 올바르지 않습니다.");
                } else {
                    // 대출 시 저장된 학생 정보와 입력된 정보가 일치하는지 확인
                    if (!book.borrowedBy.equals(returnStudentName) || !book.grade.equals(returnGrade)) {
                        JOptionPane.showMessageDialog(this, "반납 실패: 입력된 학생 정보가 대출 정보와 일치하지 않습니다.");
                    } else {
                        boolean result = bookList.returnBook(barcode);
                        if (result) {
                            JOptionPane.showMessageDialog(this, "반납이 완료되었습니다.");
                            mainGUI.loadBookList(); // 메인 GUI 테이블 갱신
                            dispose(); // 작업 후 창 자동 닫힘
                        } else {
                            JOptionPane.showMessageDialog(this, "반납 실패: 반납 처리 중 오류가 발생했습니다.");
                        }
                    }
                }
            }
        });
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}