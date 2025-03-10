import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import LJY.BookList;

import java.util.List;

public class GUI extends JFrame {

    private JTextField searchInput;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private final BookList bookList;
    private JLabel statLabel; // 통계 표시용 라벨

    // 초등학생 친화적 폰트 (맑은 고딕, 24pt)
    private Font childFont = new Font("맑은 고딕", Font.PLAIN, 24);

    public GUI(BookList bookList) {
        this.bookList = bookList;

        // 작업 경로 디버깅 출력
        System.out.println("작업 경로: " + System.getProperty("user.dir"));
        if (bookList == null) {
            JOptionPane.showMessageDialog(this, "BookList 로드 실패", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        setTitle("GLCS 도서관 검색 도우미");
        // 전체화면 전용 창을 위해 undecorated 설정
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 창 닫힐 때 데이터 저장
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                bookList.saveData();
            }
        });

        // 창 크기를 화면 크기로 하고 테두리 제거 (최소화되지 않게 방지)
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true); // 타이틀 바 및 테두리 제거
        setAlwaysOnTop(true); // 항상 위에 떠 있게 함 (중요)


        // 초등학생 친화적 디자인을 위한 배경색 설정 (밝은 파스텔톤)
        Color bgColor = new Color(255, 245, 238);  // 블랜치드 알몬드
        Color panelColor = new Color(255, 240, 245); // 라이트 핑크
        getContentPane().setBackground(bgColor);

        // ---- 상단 컨테이너 구성 (검색 패널 + 장르 패널) ----
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(panelColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // "책 제목 검색:" 라벨
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel searchLabel = new JLabel("책 검색:");
        searchLabel.setFont(childFont);
        searchPanel.add(searchLabel, gbc);

        // 검색 입력 필드
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        searchInput = new JTextField(20);
        searchInput.setFont(childFont);
        searchPanel.add(searchInput, gbc);

        // "검색" 버튼
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton searchButton = new JButton("검색");
        searchButton.setFont(childFont);
        searchButton.setBackground(new Color(173, 216, 230)); // 라이트 블루
        searchPanel.add(searchButton, gbc);

        // "초기화" 버튼
        gbc.gridx = 3;
        JButton resetButton = new JButton("초기화");
        resetButton.setFont(childFont);
        resetButton.setBackground(new Color(144, 238, 144)); // 라이트 그린
        searchPanel.add(resetButton, gbc);

        // "로그인" 버튼
        gbc.gridx = 4;
        JButton loginButton = new JButton("로그인");
        loginButton.setFont(childFont);
        loginButton.setBackground(new Color(255, 182, 193)); // 라이트 핑크
        searchPanel.add(loginButton, gbc);

        // 장르 패널 (FlowLayout)
        JPanel genrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genrePanel.setBackground(panelColor);
        JLabel genreLabel = new JLabel("장르:");
        genreLabel.setFont(childFont);
        genrePanel.add(genreLabel);
        // 장르 버튼: "동화", "판타지", "교육" (윤곽선 추가)
        String[] genres = {"동화", "판타지", "교육"};
        for (String genre : genres) {
            JButton genreButton = new JButton(genre);
            genreButton.setFont(childFont);
            genreButton.setBackground(new Color(255, 228, 196)); // 라이트 코랄
            genreButton.setFocusPainted(false);
            genreButton.setBorder(new LineBorder(Color.DARK_GRAY, 1));
            genreButton.addActionListener(e -> {
                searchInput.setText(genre);
                searchBook();
            });
            genrePanel.add(genreButton);
        }
// 생성자 내에 추가
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 창 닫기 시 별도의 작업 없이 메시지 출력
                JOptionPane.showMessageDialog(GUI.this,
                        "창을 닫을 수 없습니다.\n종료 버튼을 이용해 주세요.",
                        "알림",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 상단 컨테이너 (수직 배치)
        JPanel northContainer = new JPanel();
        northContainer.setLayout(new BoxLayout(northContainer, BoxLayout.Y_AXIS));
        northContainer.setBackground(panelColor);
        northContainer.add(searchPanel);
        northContainer.add(genrePanel);
        add(northContainer, BorderLayout.NORTH);
        // ---- 상단 컨테이너 끝 ----

        // 중앙 패널 (테이블)
        String[] columnNames = {"책 제목", "위치", "대출 상태", "바코드"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bookTable = new JTable(tableModel);
        bookTable.setDefaultEditor(Object.class, null);
        bookTable.setFont(childFont);
        bookTable.setRowHeight(30);
        // "대출 상태" 열에 StatusCellRenderer 적용 (수정: "대출 가능"은 진한 초록색)
        bookTable.getColumnModel().getColumn(2).setCellRenderer(new StatusCellRenderer());
        // 바코드 컬럼 숨김
        bookTable.removeColumn(bookTable.getColumnModel().getColumn(3));
        JScrollPane tableScroll = new JScrollPane(bookTable);
        add(tableScroll, BorderLayout.CENTER);

        // 하단 패널 (통계)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(panelColor);
        statLabel = new JLabel();
        statLabel.setFont(childFont);
        bottomPanel.add(statLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        loadBookList();
        updateStatistics();

        // 액션 리스너 설정
        searchButton.addActionListener(e -> searchBook());
        searchInput.addActionListener(e -> searchBook());
        resetButton.addActionListener(e -> {
            loadBookList();
            updateStatistics();
        });
        bookTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    int selectedRow = bookTable.getSelectedRow();
                    if(selectedRow != -1) {
                        // 숨겨진 컬럼 바코드 정확히 가져오기
                        String barcode = (String) tableModel.getValueAt(bookTable.convertRowIndexToModel(selectedRow), 3);
                        if(barcode == null || barcode.isEmpty()){
                            JOptionPane.showMessageDialog(null, "바코드가 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        new BookDetailGUI(bookList, barcode, GUI.this);
                    }
                }
            }
        });

        loginButton.addActionListener(e -> new LoginGUI(GUI.this, bookList));


        setVisible(true);
    }

    public void loadBookList() {
        tableModel.setRowCount(0);
        List<BookList.Book> sortedBooks = bookList.getSortedBooks();
        for (BookList.Book book : sortedBooks) {
            tableModel.addRow(new Object[]{
                    book.title,
                    book.location,
                    book.isBorrowed ? "대출 중" : "대출 가능",
                    book.barcode
            });
        }
    }

    private void searchBook() {
        String keyword = searchInput.getText().trim();
        String recGenre = getRecommendedGenre(keyword);
        System.out.println("검색어: " + keyword + ", 추천 장르: " + recGenre);
        tableModel.setRowCount(0);
        if (recGenre != null) {
            for (BookList.Book book : bookList.getBooks().values()) {
                System.out.println("도서: " + book.title + ", 장르: " + book.genre);
                if (book.genre != null && book.genre.equalsIgnoreCase(recGenre)) {
                    tableModel.addRow(new Object[]{
                            book.title,
                            book.location,
                            book.isBorrowed ? "대출 중" : "대출 가능",
                            book.barcode
                    });
                }
            }
        } else {
            List<BookList.Book> results = bookList.searchBooksByKeyword(keyword);
            for (BookList.Book book : results) {
                tableModel.addRow(new Object[]{
                        book.title,
                        book.location,
                        book.isBorrowed ? "대출 중" : "대출 가능",
                        book.barcode
                });
            }
        }
        updateStatistics();
    }

    private void updateStatistics() {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH);
        Map<String, Integer> countMap = new HashMap<>();
        for (BookList.LogEntry log : bookList.getLogEntries()) {
            if ("대출".equals(log.action)) {
                cal.setTimeInMillis(log.timestamp);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                if (year == currentYear && month == currentMonth) {
                    countMap.put(log.barcode, countMap.getOrDefault(log.barcode, 0) + 1);
                }
            }
        }
        // countMap을 활용해서 통계 라벨 등을 업데이트하는 코드를 추가하면 됩니다.
    String bestBarcode = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                bestBarcode = entry.getKey();
                maxCount = entry.getValue();
            }
        }
        if (bestBarcode != null) {
            BookList.Book bestBook = bookList.searchByBarcode(bestBarcode);
            if (bestBook != null) {
                statLabel.setText("이번 달 가장 많이 빌려진 책: " + bestBook.title + " (" + maxCount + "회)");
            } else {
                statLabel.setText("정보 없음");
            }
        } else {
            statLabel.setText("대출 기록 없음");
        }
    }

    private String getRecommendedGenre(String input) {
        try {
            int grade = Integer.parseInt(input);
            if (grade <= 2) return "동화";
            else if (grade <= 4) return "판타지";
            else return "교육";
        } catch (NumberFormatException e) {
            String trimmed = input.trim();
            if (trimmed.equalsIgnoreCase("동화") ||
                    trimmed.equalsIgnoreCase("판타지") ||
                    trimmed.equalsIgnoreCase("교육")) {
                return trimmed;
            }
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookList bookList = new BookList();
            new GUI(bookList);
        });
    }
}
