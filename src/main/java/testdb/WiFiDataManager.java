package testdb;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class WiFiDataManager {

    // JSON 데이터를 파싱하여 JsonArray로 반환
    public static JsonArray parseWiFiJson(String jsonData) {
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        JsonObject wifiInfo = jsonObject.getAsJsonObject("TbPublicWifiInfo");
        return wifiInfo.getAsJsonArray("row");
    }

    // WiFi 데이터를 데이터베이스에 저장
    public static void saveToDatabase(JsonArray wifiArray) {
        String dbUrl = "jdbc:sqlite:C:\\Users\\enfna\\DataGripProjects\\wifi_data.db";
        String insertQuery = "INSERT OR IGNORE INTO WIFI ( " + 
        		" X_SWIFI_MGR_NO, " +
        	    " X_SWIFI_WRDOFC, " +
        	    " X_SWIFI_MAIN_NM, " +
        	    " X_SWIFI_ADRES1, " +
        	    " X_SWIFI_ADRES2, " +
        	    " X_SWIFI_INSTL_FLOOR, " +
        	    " X_SWIFI_INSTL_TY, " +
        	    " X_SWIFI_INSTL_MBY, " +
        	    " X_SWIFI_SVC_SE, " +
        	    " X_SWIFI_CMCWR, " +
        	    " X_SWIFI_CNSTC_YEAR, " +
        	    " X_SWIFI_INOUT_DOOR, " +
        	    " X_SWIFI_REMARS3, " +
        	    " LAT, " +
        	    " LNT, " +
        	    " WORK_DTTM ) VALUES ( " +
        	    " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            for (int i = 0; i < wifiArray.size(); i++) {
                JsonObject wifi = wifiArray.get(i).getAsJsonObject();

                pstmt.setString(1, wifi.get("X_SWIFI_MGR_NO").getAsString());
                pstmt.setString(2, wifi.get("X_SWIFI_WRDOFC").getAsString());
                pstmt.setString(3, wifi.get("X_SWIFI_MAIN_NM").getAsString());
                pstmt.setString(4, wifi.get("X_SWIFI_ADRES1").getAsString());
                pstmt.setString(5, wifi.get("X_SWIFI_ADRES2").getAsString());
                pstmt.setString(6, wifi.get("X_SWIFI_INSTL_FLOOR").getAsString());
                pstmt.setString(7, wifi.get("X_SWIFI_INSTL_TY").getAsString());
                pstmt.setString(8, wifi.get("X_SWIFI_INSTL_MBY").getAsString());
                pstmt.setString(9, wifi.get("X_SWIFI_SVC_SE").getAsString());
                pstmt.setString(10, wifi.get("X_SWIFI_CMCWR").getAsString());
                pstmt.setInt(11, wifi.get("X_SWIFI_CNSTC_YEAR").getAsInt());
                pstmt.setString(12, wifi.get("X_SWIFI_INOUT_DOOR").getAsString());
                pstmt.setString(13, wifi.get("X_SWIFI_REMARS3").getAsString());
                pstmt.setString(14, wifi.get("LAT").getAsString());
                pstmt.setString(15, wifi.get("LNT").getAsString());
                pstmt.setString(16, wifi.get("WORK_DTTM").getAsString());

                pstmt.addBatch(); // 배치에 추가
            }

            pstmt.executeBatch(); // 배치 실행
            System.out.println("데이터베이스에 WiFi 정보 저장 완료!");
        } catch (Exception e) {
            System.err.println("데이터베이스 저장 중 오류 발생: " + e.getMessage());
        }
    }

    // 전체 데이터를 가져오는 메서드
    public static void fetchAndSaveAllData(String apiKey) {
        String baseUrl = "http://openapi.seoul.go.kr:8088/" + apiKey + "/json/TbPublicWifiInfo/";
        int batchSize = 1000;
        int totalData = 0;

        // 1. 총 데이터 개수 확인
        try {
            String countUrl = baseUrl + "1/1";
            String countData = fetchData(countUrl);
            JsonObject countJson = JsonParser.parseString(countData).getAsJsonObject();
            totalData = countJson.getAsJsonObject("TbPublicWifiInfo").get("list_total_count").getAsInt();
            System.out.println("총 데이터 개수: " + totalData);
        } catch (Exception e) {
            System.err.println("총 데이터 개수 확인 중 오류 발생: " + e.getMessage());
            return;
        }

        // 2. 데이터 나눠서 가져오기
        for (int startIndex = 1; startIndex <= totalData; startIndex += batchSize) {
            int endIndex = Math.min(startIndex + batchSize - 1, totalData);
            String requestUrl = baseUrl + startIndex + "/" + endIndex;

            try {
                String jsonData = fetchData(requestUrl);
                JsonArray wifiArray = parseWiFiJson(jsonData);
                saveToDatabase(wifiArray); // DB에 저장
                System.out.println(startIndex + "부터 " + endIndex + "까지 데이터를 처리했습니다.");
            } catch (Exception e) {
                System.err.println("데이터 가져오는 중 오류 발생: " + e.getMessage());
            }
        }
    }

    // API 데이터를 가져오는 메서드
    public static String fetchData(String apiUrl) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        } catch (Exception e) {
            System.err.println("API 데이터 가져오기 실패: " + e.getMessage());
            return null;
        }
        return sb.toString();
    }

    // main 메서드: 전체 흐름 실행
    public static void main(String[] args) {
        String apiKey = "62614b53546a6a613131376974444f49";
        fetchAndSaveAllData(apiKey);
    }
}
