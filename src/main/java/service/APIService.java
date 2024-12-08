package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import dao.WifiDAO;
import model.WifiInfo;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class APIService {
    private static String API_URL = "http://openapi.seoul.go.kr:8088/62614b53546a6a613131376974444f49/json/TbPublicWifiInfo/";
    private static OkHttpClient okHttpClient = new OkHttpClient();

    public static int WifiTotalCount() throws IOException {
        int count = 0;
        URL url = new URL(API_URL + "1/1");
        Request.Builder builder = new Request.Builder().url(url).get();
        Response response = okHttpClient.newCall(builder.build()).execute();

        try (ResponseBody responseBody = response.body()) {
            if (response.isSuccessful() && responseBody != null) {
                JsonElement jsonElement = JsonParser.parseString(responseBody.string());
                count = jsonElement.getAsJsonObject().get("TbPublicWifiInfo")
                        .getAsJsonObject().get("list_total_count").getAsInt();
                System.out.println("Total WiFi count: " + count);
            } else {
                System.out.println("API call failed: " + response.code());
            }
        }
        return count;
    }

    public static int getPublicWifiJson() throws IOException {
        int totalCnt = WifiTotalCount();
        int savedCount = 0;

        for (int i = 0; i <= totalCnt / 1000; i++) {
            int start = 1 + (1000 * i);
            int end = Math.min((i + 1) * 1000, totalCnt);
            URL url = new URL(API_URL + start + "/" + end);

            Request.Builder builder = new Request.Builder().url(url).get();
            Response response = okHttpClient.newCall(builder.build()).execute();

            try (ResponseBody responseBody = response.body()) {
                if (response.isSuccessful() && responseBody != null) {
                    JsonElement jsonElement = JsonParser.parseString(responseBody.string());
                    JsonArray jsonArray = jsonElement.getAsJsonObject().get("TbPublicWifiInfo")
                            .getAsJsonObject().get("row").getAsJsonArray();

                    List<WifiInfo> wifiList = new ArrayList<>();
                    for (JsonElement element : jsonArray) {
                        WifiInfo wifi = parseJsonToWifiInfo(element.getAsJsonObject());
                        wifiList.add(wifi);
                    }

                    savedCount += WifiDAO.insertPublicWifi(wifiList);
                } else {
                    System.out.println("API call failed: " + response.code());
                }
            } catch (IOException e) {
                System.err.println("Error processing API response: " + e.getMessage());
                throw e; 
            }
        }
        return savedCount;
    }

    private static WifiInfo parseJsonToWifiInfo(JsonObject jsonObject) {
        WifiInfo wifi = new WifiInfo();

        wifi.setMgrNo(getStringOrDefault(jsonObject, "X_SWIFI_MGR_NO"));
        wifi.setWrdofc(getStringOrDefault(jsonObject, "X_SWIFI_WRDOFC"));
        wifi.setMainNm(getStringOrDefault(jsonObject, "X_SWIFI_MAIN_NM"));
        wifi.setAdres1(getStringOrDefault(jsonObject, "X_SWIFI_ADRES1"));
        wifi.setAdres2(getStringOrDefault(jsonObject, "X_SWIFI_ADRES2"));
        wifi.setInstlFloor(getStringOrDefault(jsonObject, "X_SWIFI_INSTL_FLOOR"));
        wifi.setInstlTy(getStringOrDefault(jsonObject, "X_SWIFI_INSTL_TY"));
        wifi.setInstlMby(getStringOrDefault(jsonObject, "X_SWIFI_INSTL_MBY"));
        wifi.setSvcSe(getStringOrDefault(jsonObject, "X_SWIFI_SVC_SE"));
        wifi.setCmcwr(getStringOrDefault(jsonObject, "X_SWIFI_CMCWR"));
        wifi.setCnstcYear(jsonObject.get("X_SWIFI_CNSTC_YEAR").getAsInt());
        wifi.setInoutDoor(getStringOrDefault(jsonObject, "X_SWIFI_INOUT_DOOR"));
        wifi.setRemars3(getStringOrDefault(jsonObject, "X_SWIFI_REMARS3"));
        wifi.setLat(getStringOrDefault(jsonObject, "LAT"));
        wifi.setLnt(getStringOrDefault(jsonObject, "LNT"));
        wifi.setWorkDttm(getStringOrDefault(jsonObject, "WORK_DTTM"));

        return wifi;
    }

    private static String getStringOrDefault(JsonObject jsonObject, String key) {
        return jsonObject.has(key) ? jsonObject.get(key).getAsString() : "";
    }
}
