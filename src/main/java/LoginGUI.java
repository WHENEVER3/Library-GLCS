import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import LJY.BookList;

public class LoginGUI extends JDialog {
    private final BookList bookList;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private final String validUsername = "glcs";
    private final String validPassword = "glcsglcs";

    public LoginGUI(JFrame parent, BookList bookList) {
        super(parent, "로그인", false);
        this.bookList = bookList;

        setLayout(new GridLayout(3, 2, 10, 10));
        setAlwaysOnTop(true);

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("로그인");

        add(new JLabel("아이디:", JLabel.CENTER));
        add(usernameField);
        add(new JLabel("비밀번호:", JLabel.CENTER));
        add(passwordField);
        add(new JLabel());
        add(loginButton);

        ActionListener loginAction = e -> performLogin();
        usernameField.addActionListener(loginAction);
        passwordField.addActionListener(loginAction);
        loginButton.addActionListener(loginAction);

        pack();
        setSize(400, 200);
        setLocationRelativeTo(getParent());
        setVisible(true);
    }

    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // 하나의 조건문으로 처리 (validUsername, validPassword 사용)
        if (validUsername.equals(username) && validPassword.equals(password)) {
            SwingUtilities.invokeLater(() -> {
                new BorrowHistoryGUI(bookList, username);
                dispose();
            });
        } else {
            JOptionPane.showMessageDialog(this,
                    "아이디 또는 비밀번호가 올바르지 않습니다.\n입력하신 값:\n아이디: " + username + "\n비밀번호: " + password,
                    "로그인 실패",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
