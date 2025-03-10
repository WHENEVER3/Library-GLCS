// DBHelper.java
package LJY;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {
    private static final String DB_URL = "jdbc:sqlite:" + System.getProperty("user.home") + "/library.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS books (" +
                "barcode TEXT PRIMARY KEY," +
                "title TEXT NOT NULL," +
                "location TEXT NOT NULL," +
                "isBorrowed INTEGER NOT NULL," +
                "borrowedBy TEXT," +
                "grade TEXT," +
                "borrowTime INTEGER" +
                ");";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
