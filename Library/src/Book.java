import java.io.Serializable;

public  class Book implements Serializable {
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

    public long getElapsedTime() {
        if (!isBorrowed) return 0;
        long currentTime = System.currentTimeMillis();
        return (currentTime - borrowTime) / (1000 * 60 * 60);
    }
}
