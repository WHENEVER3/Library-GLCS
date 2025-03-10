import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String location;
    private String barcode;
    private boolean isBorrowed;
    private String borrowedBy; // 대출자 이름
    private String grade;      // 학년
    private long borrowTime;   // 대출 시각 (밀리초)

    public Book(String title, String location, String barcode) {
        this.title = title;
        this.location = location;
        this.barcode = barcode;
        this.isBorrowed = false;
        this.borrowedBy = "";
        this.grade = "";
        this.borrowTime = 0;
    }

    /**
     * 현재 대출 상태에 따른 경과 시간(시간 단위)을 반환합니다.
     * 대출 중이 아니라면 0을 반환합니다.
     */
    public long getElapsedTime() {
        if (!isBorrowed) return 0;
        long currentTime = System.currentTimeMillis();
        return (currentTime - borrowTime) / (1000 * 60 * 60);
    }

    // Getter 및 Setter 메서드

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getBarcode() {
        return barcode;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    public String getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(String borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public long getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(long borrowTime) {
        this.borrowTime = borrowTime;
    }
}
