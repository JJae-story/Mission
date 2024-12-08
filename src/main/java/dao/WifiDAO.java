package dao;

import model.WifiInfo;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class WifiDAO {
    private static final String url = "jdbc:sqlite:C:/Users/enfna/DataGripProjects/wifi_data.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    // 특정 Wi-Fi 정보 가져오기 
    public static WifiInfo getWifiInfoById(String mgrNo, double lat, double lnt) {
        WifiInfo wifi = null;
        String query = "SELECT * FROM WIFI WHERE X_SWIFI_MGR_NO = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, mgrNo);  

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                wifi = new WifiInfo();
                wifi.setMgrNo(rs.getString("X_SWIFI_MGR_NO"));
                wifi.setWrdofc(rs.getString("X_SWIFI_WRDOFC"));
                wifi.setMainNm(rs.getString("X_SWIFI_MAIN_NM"));
                wifi.setAdres1(rs.getString("X_SWIFI_ADRES1"));
                wifi.setAdres2(rs.getString("X_SWIFI_ADRES2"));
                wifi.setInstlFloor(rs.getString("X_SWIFI_INSTL_FLOOR"));
                wifi.setInstlTy(rs.getString("X_SWIFI_INSTL_TY"));
                wifi.setInstlMby(rs.getString("X_SWIFI_INSTL_MBY"));
                wifi.setSvcSe(rs.getString("X_SWIFI_SVC_SE"));
                wifi.setCmcwr(rs.getString("X_SWIFI_CMCWR"));
                wifi.setCnstcYear(rs.getInt("X_SWIFI_CNSTC_YEAR"));
                wifi.setInoutDoor(rs.getString("X_SWIFI_INOUT_DOOR"));
                wifi.setRemars3(rs.getString("X_SWIFI_REMARS3"));
                wifi.setLat(rs.getString("LAT"));
                wifi.setLnt(rs.getString("LNT"));
                wifi.setWorkDttm(rs.getString("WORK_DTTM"));

                double latDouble = Double.parseDouble(rs.getString("LAT"));
                double lntDouble = Double.parseDouble(rs.getString("LNT"));
                double distance = calculateDistance(lat, lnt, latDouble, lntDouble);
                wifi.setDistance(distance); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wifi;
    }
    
    public static WifiInfo getWifiInfo(String mgrNo) {
        WifiInfo wifi = null;
        String query = "SELECT * FROM WIFI WHERE X_SWIFI_MGR_NO = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, mgrNo); 

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                wifi = new WifiInfo();
                wifi.setMgrNo(rs.getString("X_SWIFI_MGR_NO"));
                wifi.setWrdofc(rs.getString("X_SWIFI_WRDOFC"));
                wifi.setMainNm(rs.getString("X_SWIFI_MAIN_NM"));
                wifi.setAdres1(rs.getString("X_SWIFI_ADRES1"));
                wifi.setAdres2(rs.getString("X_SWIFI_ADRES2"));
                wifi.setInstlFloor(rs.getString("X_SWIFI_INSTL_FLOOR"));
                wifi.setInstlTy(rs.getString("X_SWIFI_INSTL_TY"));
                wifi.setInstlMby(rs.getString("X_SWIFI_INSTL_MBY"));
                wifi.setSvcSe(rs.getString("X_SWIFI_SVC_SE"));
                wifi.setCmcwr(rs.getString("X_SWIFI_CMCWR"));
                wifi.setCnstcYear(rs.getInt("X_SWIFI_CNSTC_YEAR"));
                wifi.setInoutDoor(rs.getString("X_SWIFI_INOUT_DOOR"));
                wifi.setRemars3(rs.getString("X_SWIFI_REMARS3"));
                wifi.setLat(rs.getString("LAT"));
                wifi.setLnt(rs.getString("LNT"));
                wifi.setWorkDttm(rs.getString("WORK_DTTM"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wifi; 
    }

    // 거리 계산 함수 
    private static double calculateDistance(double lat1, double lnt1, double lat2, double lnt2) {
        final int R = 6371; // 지구 반지름 (킬로미터)
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lnt2 - lnt1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // 거리 반환 (단위: 킬로미터)
    }

    // 가까운 Wi-Fi 20개 가져오기 
    public static List<WifiInfo> getNearbyWifi(double lat, double lnt) {
        List<WifiInfo> wifiList = new ArrayList<>();
        String query = "SELECT *, " +
                "(6371 * acos(cos(radians(?)) * cos(radians(LAT)) * cos(radians(LNT) - radians(?)) + sin(radians(?)) * sin(radians(LAT)))) AS distance " +
                "FROM WIFI " +
                "ORDER BY distance " +
                "LIMIT 20";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, lat);
            pstmt.setDouble(2, lnt);
            pstmt.setDouble(3, lat);

            ResultSet rs = pstmt.executeQuery();
            DecimalFormat df = new DecimalFormat("#.####");

            while (rs.next()) {
                WifiInfo wifi = new WifiInfo();
                wifi.setDistance(Double.parseDouble(df.format(rs.getDouble("distance")))); // 소숫점 4자리
                wifi.setMgrNo(rs.getString("X_SWIFI_MGR_NO"));
                wifi.setWrdofc(rs.getString("X_SWIFI_WRDOFC"));
                wifi.setMainNm(rs.getString("X_SWIFI_MAIN_NM"));
                wifi.setAdres1(rs.getString("X_SWIFI_ADRES1"));
                wifi.setAdres2(rs.getString("X_SWIFI_ADRES2"));
                wifi.setInstlFloor(rs.getString("X_SWIFI_INSTL_FLOOR"));
                wifi.setInstlTy(rs.getString("X_SWIFI_INSTL_TY"));
                wifi.setInstlMby(rs.getString("X_SWIFI_INSTL_MBY"));
                wifi.setSvcSe(rs.getString("X_SWIFI_SVC_SE"));
                wifi.setCmcwr(rs.getString("X_SWIFI_CMCWR"));
                wifi.setCnstcYear(rs.getInt("X_SWIFI_CNSTC_YEAR"));
                wifi.setInoutDoor(rs.getString("X_SWIFI_INOUT_DOOR"));
                wifi.setRemars3(rs.getString("X_SWIFI_REMARS3"));
                wifi.setLat(rs.getString("LAT"));
                wifi.setLnt(rs.getString("LNT"));
                wifi.setWorkDttm(rs.getString("WORK_DTTM"));

                wifiList.add(wifi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wifiList;
    }

    // Open API 데이터 저장 메서드 
    public static int insertPublicWifi(List<WifiInfo> wifiList) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC 드라이버를 로드할 수 없습니다.");
            e.printStackTrace();
            return 0;  
        }

        String insertQuery = "INSERT INTO WIFI ( " +
                "X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, " +
                "X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, " +
                "X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, " +
                "X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, " +
                "X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int count = 0;

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            for (WifiInfo wifi : wifiList) {
                pstmt.setString(1, wifi.getMgrNo());
                pstmt.setString(2, wifi.getWrdofc());
                pstmt.setString(3, wifi.getMainNm());
                pstmt.setString(4, wifi.getAdres1());
                pstmt.setString(5, wifi.getAdres2());
                pstmt.setString(6, wifi.getInstlFloor());
                pstmt.setString(7, wifi.getInstlTy());
                pstmt.setString(8, wifi.getInstlMby());
                pstmt.setString(9, wifi.getSvcSe());
                pstmt.setString(10, wifi.getCmcwr());
                pstmt.setInt(11, wifi.getCnstcYear());
                pstmt.setString(12, wifi.getInoutDoor());
                pstmt.setString(13, wifi.getRemars3());
                pstmt.setString(14, wifi.getLat());
                pstmt.setString(15, wifi.getLnt());
                pstmt.setString(16, wifi.getWorkDttm());

                pstmt.addBatch();
                count++;
            }

            pstmt.executeBatch(); 
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count; 
    }
}
