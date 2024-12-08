package dao;

import model.Bookmark;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkDAO {

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

    // 북마크 추가
    public static int addBookmark(Bookmark bookmark) {
        String sql = "INSERT INTO BOOKMARK (GROUP_NAME, WIFI_NAME, CREATED_AT, REMARKS) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bookmark.getGroupName());
            stmt.setString(2, bookmark.getWifiName());  
            stmt.setString(3, bookmark.getCreatedAt()); 
            stmt.setString(4, bookmark.getRemarks());  
            
            int rowsAffected = stmt.executeUpdate();  
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 북마크 조회 (특정 그룹에 속한 북마크들을 조회)
    public static List<Bookmark> getBookmarksByGroup(String groupName) {
        List<Bookmark> bookmarks = new ArrayList<>();
        String query = "SELECT * FROM BOOKMARK WHERE GROUP_ID = ?"; 

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, groupName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Bookmark bookmark = new Bookmark();
                bookmark.setId(rs.getInt("ID"));
                bookmark.setGroupName(rs.getString("GROUP_ID"));
                bookmark.setWifiName(rs.getString("WIFI_NAME"));
                bookmark.setCreatedAt(rs.getString("CREATED_AT"));
                bookmark.setRemarks(rs.getString("REMARKS"));
                bookmarks.add(bookmark);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching bookmarks by group: " + e.getMessage());
            e.printStackTrace();
        }

        return bookmarks;
    }

    // 모든 북마크 조회 (ID를 기준으로)
    public static List<Bookmark> getAllBookmarks() {
        List<Bookmark> bookmarks = new ArrayList<>();
        String sql = "SELECT * FROM BOOKMARK";
        
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bookmark bookmark = new Bookmark(
                    rs.getString("GROUP_NAME"),
                    rs.getString("WIFI_NAME"),
                    rs.getString("CREATED_AT"),
                    rs.getString("REMARKS")
                );
                bookmark.setId(rs.getInt("ID"));
                bookmarks.add(bookmark);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookmarks;
    }
    
    // 북마크 삭제 (ID로 삭제)
    public boolean deleteBookmark(int id) {
        String sql = "DELETE FROM BOOKMARK WHERE ID = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 특정 북마크 조회 (ID 기준)
    public Bookmark getBookmarkById(int id) {
        String sql = "SELECT * FROM BOOKMARK WHERE ID = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Bookmark bookmark = new Bookmark();
                bookmark.setId(rs.getInt("ID"));
                bookmark.setGroupName(rs.getString("GROUP_NAME"));
                bookmark.setWifiName(rs.getString("WIFI_NAME"));
                bookmark.setCreatedAt(rs.getTimestamp("CREATED_AT").toString());
                return bookmark;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
