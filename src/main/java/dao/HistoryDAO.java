package dao;

import model.History;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {
    // 데이터베이스 연결 메서드
    private static Connection connect() {
    	try {
            Class.forName("org.sqlite.JDBC"); // SQLite 드라이버 로드
            return DriverManager.getConnection("jdbc:sqlite:C:/Users/enfna/DataGripProjects/wifi_data.db");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed.");
            e.printStackTrace();
        }
        return null;
    }

    // 히스토리 추가 메서드
    public static int addHistory(double lat, double lnt, String searchDttm) {
        String query = "INSERT INTO HISTORY (LAT, LNT, SEARCH_DTTM) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, lat);
            pstmt.setDouble(2, lnt);
            pstmt.setString(3, searchDttm);
            return pstmt.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 히스토리 조회 메서드
    public static List<History> getAllHistory() {
        List<History> historyList = new ArrayList<>();
        String query = "SELECT * FROM HISTORY ORDER BY ID DESC";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                History history = new History();
                history.setId(rs.getInt("ID"));
                history.setLat(rs.getDouble("LAT"));
                history.setLnt(rs.getDouble("LNT"));
                history.setSearchDttm(rs.getString("SEARCH_DTTM"));
                history.setRemarks("삭제");
                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historyList;
    }

    // 히스토리 삭제 메서드
    public static int deleteHistoryById(int id) {
        String query = "DELETE FROM HISTORY WHERE ID = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
