package LJY;

import javax.swing.JOptionPane;
import java.io.*;
import java.util.*;

public class BookList implements Serializable {
    private static final long serialVersionUID = 1L;

    // 책 데이터 저장 (바코드를 키로 사용)
    private Map<String, Book> bookDatabase = new HashMap<>();

    // 로그 데이터를 저장할 리스트 (대출/반납 기록)
    private List<LogEntry> logEntries = new ArrayList<>();

    // 데이터 파일 경로 (원하는 경로로 변경)
    private static final String DATA_FILE = System.getProperty("user.home") + File.separator + "booklist.dat";

    // 생성자: 프로그램 시작 시 저장된 데이터를 불러옵니다.
    public BookList() {
        loadData();
    }

    // CSV 파일로부터 책 목록을 불러오는 메서드 (책 제목, 위치, 장르)
    public void importBooksFromCSV(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(csvFilePath), "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;  // 빈 줄 건너뛰기

                // CSV 파일 형식: 책 제목,위치,장르
                String[] tokens = line.split(",");
                if (tokens.length < 3) continue;  // 최소 3개의 값 필요

                String title = tokens[0].trim();
                String location = tokens[1].trim();
                String genre = tokens[2].trim();

                // 바코드는 UUID를 사용하여 생성 (고유성 보장)
                String barcode = UUID.randomUUID().toString();

                // 새 책 객체 생성 및 장르 설정
                Book newBook = new Book(title, location, barcode);
                newBook.genre = genre;

                // 중복 없이 추가
                bookDatabase.put(barcode, newBook);
            }
            saveData();
            JOptionPane.showMessageDialog(null, "도서 목록이 성공적으로 불러와졌습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "도서 목록 불러오기 오류:\n" + e.getMessage());
        }
    }

    // 책 추가 메서드
    public boolean addBook(String title, String location, String barcode) {
        if (bookDatabase.containsKey(barcode)) {
            return false;
        }
        Book newBook = new Book(title, location, barcode);
        bookDatabase.put(barcode, newBook);
        saveData();
        return true;
    }

    // 책 삭제 메서드
    public boolean deleteBook(String barcode) {
        if (bookDatabase.containsKey(barcode)) {
            bookDatabase.remove(barcode);
            saveData();
            return true;
        }
        return false;
    }

    // 바코드로 책 검색
    public Book searchByBarcode(String barcode) {
        return bookDatabase.get(barcode);
    }

    // 책 대출 처리 메서드
    public boolean borrowBook(String barcode, String studentName, String grade) {
        Book book = bookDatabase.get(barcode);
        if (book != null && !book.isBorrowed) {
            book.isBorrowed = true;
            book.borrowedBy = studentName;
            book.grade = grade;
            book.borrowTime = System.currentTimeMillis();
            // 로그 기록 추가 (grade 전달)
            logEntries.add(new LogEntry(barcode, book.title, "대출", studentName, book.borrowTime, grade));
            saveData();
            return true;
        }
        return false;
    }

    // 책 반납 처리 메서드
    public boolean returnBook(String barcode) {
        Book book = bookDatabase.get(barcode);
        if (book != null && book.isBorrowed) {
            book.isBorrowed = false;
            String borrower = book.borrowedBy;
            book.borrowedBy = "";
            // 반납 시에는 학년은 그대로 유지하거나, 필요하면 처리
            logEntries.add(new LogEntry(barcode, book.title, "반납", borrower, System.currentTimeMillis(), book.grade));
            book.grade = "";
            book.borrowTime = 0;
            saveData();
            return true;
        }
        return false;
    }

    public Map<String, Book> getBooks() {
        return bookDatabase;
    }

    public List<Book> searchBooksByKeyword(String keyword) {
        List<Book> results = new ArrayList<>();
        String normalizedKeyword = keyword.trim().toLowerCase();
        for (Book book : bookDatabase.values()) {
            if (book.title.toLowerCase().contains(normalizedKeyword)) {
                results.add(book);
            }
        }
        return results;
    }

    public List<LogEntry> getLogEntries() {
        return logEntries;
    }

    public List<Book> getSortedBooks() {
        List<Book> list = new ArrayList<>(bookDatabase.values());
        list.sort((b1, b2) -> b1.title.compareToIgnoreCase(b2.title));
        return list;
    }

    // saveData(): 데이터를 파일에 저장 (직렬화)
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(this);
            System.out.println("데이터 저장됨: " + DATA_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // loadData(): 파일로부터 데이터를 불러와 현재 BookList 객체를 복원
    public void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("저장된 데이터 파일이 없습니다.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            BookList loaded = (BookList) ois.readObject();
            this.bookDatabase = loaded.bookDatabase;
            this.logEntries = loaded.logEntries;
            System.out.println("데이터 로드됨: " + DATA_FILE);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 내부 Book 클래스
    public static class Book implements Serializable {
        private static final long serialVersionUID = 1L;
        public String title;
        public String location;
        public String barcode;
        public boolean isBorrowed;
        public String borrowedBy;
        public String grade;
        public long borrowTime;
        public String genre;

        public Book(String title, String location, String barcode) {
            this.title = title;
            this.location = location;
            this.barcode = barcode;
            this.isBorrowed = false;
            this.borrowedBy = "";
            this.grade = "";
            this.genre = "";
            this.borrowTime = 0;
        }
    }

    // 내부 LogEntry 클래스 (수정됨: grade 필드 추가, 생성자 6개 인자)
    public static class LogEntry implements Serializable {
        private static final long serialVersionUID = 1L;
        public String barcode;
        public String title;
        public String action; // "대출" 또는 "반납"
        public String user;
        public long timestamp;
        public String grade;

        public LogEntry(String barcode, String title, String action, String user, long timestamp, String grade) {
            this.barcode = barcode;
            this.title = title;
            this.action = action;
            this.user = user;
            this.timestamp = timestamp;
            this.grade = grade;
        }
    }
}
