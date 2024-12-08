package testdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
	private static final String dbFilePath = "C:\\\\Users\\\\enfna\\\\DataGripProjects\\\\wifi_data.db";
	private static final String url = "jdbc:sqlite:" + dbFilePath;

    public static Connection connect() {
        Connection conn = null;
        try {
            // SQLite 연결
            conn = DriverManager.getConnection(url);
            System.out.println("SQLite에 연결되었습니다.");
        } catch (SQLException e) {
            System.out.println("연결에 실패했습니다: " + e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        connect();
    }
}

