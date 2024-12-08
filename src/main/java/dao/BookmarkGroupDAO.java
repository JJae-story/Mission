package dao;

import model.BookmarkGroup;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BookmarkGroupDAO {

    // 데이터베이스 연결 메서드
	private static Connection connect() {
	    try {
	        Class.forName("org.sqlite.JDBC");
	        return DriverManager.getConnection("jdbc:sqlite:C:/Users/enfna/DataGripProjects/wifi_data.db");
	    } catch (ClassNotFoundException e) {
	        System.err.println("SQLite JDBC Driver not found.");
	        e.printStackTrace();
	    } catch (SQLException e) {
	        System.err.println("Database connection failed. Please check your database URL and credentials.");
	        e.printStackTrace();
	    }
	    return null;
	}

    // 북마크 그룹 추가 메서드
    public static int addBookmarkGroup(String name, int orderNum) {
        int result = 0;
        String query = "INSERT INTO BOOKMARK_GROUP (NAME, ORDER_NUM, CREATED_AT, UPDATED_AT) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setInt(2, orderNum);
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            stmt.setNull(4, java.sql.Types.TIMESTAMP);

            result = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 북마크 그룹 삭제 메서드
    public static int deleteBookmarkGroupById(int id) {
        String query = "DELETE FROM BOOKMARK_GROUP WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 북마크 그룹 수정 메서드
    public static int updateBookmarkGroup(int id, String name, int orderNum) {
        int result = 0;
        String query = "UPDATE BOOKMARK_GROUP SET NAME = ?, ORDER_NUM = ?, UPDATED_AT = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setInt(2, orderNum);
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis())); 
            stmt.setInt(4, id);

            result = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 북마크 그룹 목록 가져오기 메서드
    public static List<BookmarkGroup> getAllBookmarkGroups() {
        List<BookmarkGroup> groups = new ArrayList<>();
        String query = "SELECT * FROM BOOKMARK_GROUP ORDER BY ORDER_NUM ASC";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 날짜 형식 설정

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                BookmarkGroup group = new BookmarkGroup();
                group.setId(rs.getInt("ID"));
                group.setName(rs.getString("NAME"));
                group.setOrderNum(rs.getInt("ORDER_NUM"));

                // 날짜 포맷 적용
                Timestamp createdAtTimestamp = rs.getTimestamp("CREATED_AT");
                Timestamp updatedAtTimestamp = rs.getTimestamp("UPDATED_AT");

                group.setCreatedAt(createdAtTimestamp != null ? sdf.format(createdAtTimestamp) : "");
                group.setUpdatedAt(updatedAtTimestamp != null ? sdf.format(updatedAtTimestamp) : "");

                groups.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groups;
    }

    // 특정 북마크 그룹 조회 메서드
    public static BookmarkGroup getBookmarkGroupById(int id) {
        String query = "SELECT * FROM BOOKMARK_GROUP WHERE id = ?";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 날짜 형식 설정

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    BookmarkGroup group = new BookmarkGroup();
                    group.setId(rs.getInt("ID"));
                    group.setName(rs.getString("NAME"));
                    group.setOrderNum(rs.getInt("ORDER_NUM"));

                    // 날짜 포맷 적용
                    Timestamp createdAtTimestamp = rs.getTimestamp("CREATED_AT");
                    Timestamp updatedAtTimestamp = rs.getTimestamp("UPDATED_AT");

                    group.setCreatedAt(createdAtTimestamp != null ? sdf.format(createdAtTimestamp) : "");
                    group.setUpdatedAt(updatedAtTimestamp != null ? sdf.format(updatedAtTimestamp) : "");

                    return group;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
