import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import LJY.BookList;
import LJY.BookList.Book;

public class GUI extends JFrame {
    private JTextField searchInput;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private final BookList bookList;

    // BookList 인자를 받는 생성자
    public GUI(BookList bookList) {
        this.bookList = bookList;

        if (bookList == null) {
            JOptionPane.showMessageDialog(this, "📛 오류: BookList가 로드되지 않았습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        setTitle("GLCS 도서관 검색 도우미");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 창이 닫힐 때 데이터를 파일에 저장하도록 WindowListener 추가
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                bookList.saveData();
            }
        });

        // 전체 화면으로 실행
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // 공통 폰트 설정 (글씨 크기 18pt)
        Font uiFont = new Font("SansSerif", Font.PLAIN, 18);

        // 검색 패널 생성 (왼쪽)
        JPanel searchPanel = new JPanel();
        JLabel searchLabel = new JLabel("책 제목 검색:");
        searchLabel.setFont(uiFont);
        searchPanel.add(searchLabel);

        searchInput = new JTextField(20);
        searchInput.setFont(uiFont);
        searchPanel.add(searchInput);

        JButton searchButton = new JButton("검색");
        searchButton.setFont(uiFont);
        searchPanel.add(searchButton);

        JButton resetButton = new JButton("초기화");
        resetButton.setFont(uiFont);
        searchPanel.add(resetButton);

        // 로그인 패널 생성 (오른쪽)
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton loginButton = new JButton("로그인");
        loginButton.setFont(uiFont);
        loginPanel.add(loginButton);

        // 상단 패널에 검색패널과 로그인패널 배치
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(loginPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // 테이블 생성 및 배치
        // 4개 컬럼: 책 제목, 위치, 대출 상태, 바코드 (바코드는 숨김 처리)
        String[] columnNames = {"책 제목", "위치", "대출 상태", "바코드"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bookTable = new JTable(tableModel);
        bookTable.setDefaultEditor(Object.class, null); // 셀 수정 방지

        // 테이블 폰트 설정
        bookTable.setFont(uiFont);
        bookTable.setRowHeight(30);

        // 바코드 컬럼(인덱스 3)을 화면에서 숨김 처리 (모델에는 남아 있음)
        bookTable.removeColumn(bookTable.getColumnModel().getColumn(3));

        JScrollPane tableScroll = new JScrollPane(bookTable);
        add(tableScroll, BorderLayout.CENTER);

        loadBookList();

        searchButton.addActionListener(e -> searchBook());
        searchInput.addActionListener(e -> searchBook());
        resetButton.addActionListener(e -> loadBookList());

        bookTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = bookTable.getSelectedRow();
                    if (selectedRow != -1) {
                        // 모델 상의 인덱스 3(바코드) 사용
                        String barcode = (String) tableModel.getValueAt(selectedRow, 3);
                        if (barcode == null || barcode.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "📛 오류: 바코드가 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        new BookDetailGUI(bookList, barcode, GUI.this);
                    }
                }
            }
        });

        // 로그인 버튼 클릭 시 LoginGUI 호출
        loginButton.addActionListener(e -> new LoginGUI(bookList));

        setVisible(true);
    }

    public void loadBookList() {
        tableModel.setRowCount(0);
        for (BookList.Book book : bookList.getBooks().values()) {
            tableModel.addRow(new Object[]{
                    book.title,
                    book.location,
                    book.isBorrowed ? "대출 중" : "대출 가능",
                    book.barcode  // 모델에 저장되지만 화면에서는 보이지 않음
            });
        }
    }

    private void searchBook() {
        String keyword = searchInput.getText().trim();
        List<BookList.Book> results = bookList.searchBooksByKeyword(keyword);

        tableModel.setRowCount(0);
        for (BookList.Book book : results) {
            tableModel.addRow(new Object[]{
                    book.title,
                    book.location,
                    book.isBorrowed ? "대출 중" : "대출 가능",
                    book.barcode
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUI(new BookList());
        });
    }
}
