package LJY;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class BookList {
    private HashMap<String, Book> bookDatabase;

    // 데이터 로드를 시도하고, 데이터가 없으면 기본 데이터를 추가하는 생성자
    public BookList() {
        loadData();
        if (bookDatabase == null || bookDatabase.isEmpty()) {
            bookDatabase = new HashMap<>();
            addBooks();
        }
    }

    // Book 클래스: 외부 접근 가능하도록 public 선언 및 추가 필드, 메서드 구현
    public static class Book implements Serializable {
        private static final long serialVersionUID = 1L;
        public String title;
        public String location;
        public String barcode;
        public boolean isBorrowed;
        public String borrowedBy; // 대출자 이름
        public String grade;      // 학년
        public long borrowTime;   // 대출 시각 (밀리초)

        public Book(String title, String location, String barcode) {
            this.title = title;
            this.location = location;
            this.barcode = barcode;
            this.isBorrowed = false;
            this.borrowedBy = "";
            this.grade = "";
            this.borrowTime = 0;
        }

        // 대출 경과 시간(시간 단위) 계산 메서드
        public long getElapsedTime() {
            if (!isBorrowed) return 0;
            long currentTime = System.currentTimeMillis();
            return (currentTime - borrowTime) / (1000 * 60 * 60);
        }
    }

    private void addBooks() {
        bookDatabase.put("9788998139766", new Book("해리포터", "1층 A-3 서가", "9788998139766"));
        bookDatabase.put("9788968481295", new Book("자바의 정석", "2층 B-5 서가", "9788968481295"));
        bookDatabase.put("9788932917245", new Book("어린 왕자", "1층 C-2 서가", "9788932917245"));
        bookDatabase.put("9788960773511", new Book("알고리즘 문제 해결 전략", "3층 D-1 서가", "9788960773511"));
        bookDatabase.put("9788997924217", new Book("데이터베이스 개론", "2층 E-4 서가", "9788997924217"));
    }

    public HashMap<String, Book> getBooks() {
        return bookDatabase;
    }

    public List<Book> searchBooksByKeyword(String keyword) {
        List<Book> results = new ArrayList<>();
        // 검색어의 공백 제거 및 소문자 변환
        String normalizedKeyword = keyword.replace(" ", "").toLowerCase();
        for (Book book : bookDatabase.values()) {
            // 책 제목의 공백 제거 및 소문자 변환
            String normalizedTitle = book.title.replace(" ", "").toLowerCase();
            if (normalizedTitle.contains(normalizedKeyword)) {
                results.add(book);
            }
        }
        return results;
    }

    public void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("books.dat"))) {
            out.writeObject(bookDatabase);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        File file = new File("books.dat");
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                bookDatabase = (HashMap<String, Book>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Book searchByBarcode(String barcode) {
        return bookDatabase.get(barcode);
    }

    public boolean borrowBook(String barcode, String studentName, String grade) {
        Book book = bookDatabase.get(barcode);
        if (book != null && !book.isBorrowed) {
            book.isBorrowed = true;
            book.borrowedBy = studentName;
            book.grade = grade;
            book.borrowTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }
    // BookList.java (일부 발췌)
    // 책 추가 메서드
    public boolean addBook(String title, String location, String barcode) {
        if (bookDatabase.containsKey(barcode)) {
            return false; // 이미 동일한 바코드의 책이 존재하면 실패 처리
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


    public boolean returnBook(String barcode) {
        Book book = bookDatabase.get(barcode);
        if (book != null && book.isBorrowed) {
            book.isBorrowed = false;
            book.borrowedBy = "";
            book.grade = "";
            book.borrowTime = 0;
            return true;
        }
        return false;
    }
    }
