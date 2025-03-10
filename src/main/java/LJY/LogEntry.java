package LJY;

import java.io.Serializable;

public class LogEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    public String barcode;
    public String title;
    public String action; // "대출" 또는 "반납"
    public String user;
    public long timestamp;
    public String grade;  // grade 필드 추가

    // 생성자: grade 매개변수를 포함하여 6개의 인자를 받습니다.
    public LogEntry(String barcode, String title, String action, String user, long timestamp, String grade) {
        this.barcode = barcode;
        this.title = title;
        this.action = action;
        this.user = user;
        this.timestamp = timestamp;
        this.grade = grade;
    }
}
