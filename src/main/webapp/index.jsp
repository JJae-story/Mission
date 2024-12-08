<%@page import="model.WifiInfo"%>
<%@page import="model.History"%>
<%@page import="dao.WifiDAO"%>
<%@page import="dao.HistoryDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보 구하기</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    
</head>
<body>
    <h1>와이파이 정보 구하기</h1>

    <a href="index.jsp">홈</a> &#124;
    <a href="history.jsp">위치 히스토리 목록</a> &#124;
    <a href="fetch-wifi">Open API 와이파이 정보 가져오기</a> &#124;
    <a href="bookmark.jsp">북마크 보기</a> &#124;
    <a href="bookmark-group.jsp">북마크 그룹 관리</a>

    <form class="margin" method="get" action="index.jsp">
        <label>LAT: <input type="text" name="lat" id="lat" value="0.0" required></label>
        <label>LNT: <input type="text" name="lnt" id="lnt" value="0.0" required></label>
        <button type="button" id="getLocation">내 위치 가져오기</button>
        <button type="submit">근처 WIFI 정보 보기</button>
    </form>

    <table>
        <thead>
            <tr>
                <th>거리(Km)</th>
                <th>관리번호</th>
                <th>자치구</th>
                <th>와이파이명</th>
                <th>도로명주소</th>
                <th>상세주소</th>
                <th>설치위치(층)</th>
                <th>설치유형</th>
                <th>설치기관</th>
                <th>서비스구분</th>
                <th>망종류</th>
                <th>설치년도</th>
                <th>실내외구분</th>
                <th>WIFI접속환경</th>
                <th>X좌표</th>
                <th>Y좌표</th>
                <th>작업일자</th>
            </tr>
        </thead>
        <tbody>
            <%
                String latStr = request.getParameter("lat");
                String lntStr = request.getParameter("lnt");

                if (latStr != null && lntStr != null) {
                    try {
                        double lat = Double.parseDouble(latStr);
                        double lnt = Double.parseDouble(lntStr);

                        String searchDttm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                        HistoryDAO.addHistory(lat, lnt, searchDttm);

                        List<WifiInfo> nearbyWifiList = WifiDAO.getNearbyWifi(lat, lnt);

                        if (nearbyWifiList != null && !nearbyWifiList.isEmpty()) {
                            for (WifiInfo wifi : nearbyWifiList) {
            %>
            <tr>
                <td><%= wifi.getDistance() %></td>
                <td><%= wifi.getMgrNo() %></td>
                <td><%= wifi.getWrdofc() %></td>
                <td>
				    <a href="detail.jsp?mgrNo=<%= wifi.getMgrNo() %>"><%= wifi.getMainNm() %></a>
				</td>
                <td><%= wifi.getAdres1() %></td>
                <td><%= wifi.getAdres2() %></td>
                <td><%= wifi.getInstlFloor() %></td>
                <td><%= wifi.getInstlTy() %></td>
                <td><%= wifi.getInstlMby() %></td>
                <td><%= wifi.getSvcSe() %></td>
                <td><%= wifi.getCmcwr() %></td>
                <td><%= wifi.getCnstcYear() %></td>
                <td><%= wifi.getInoutDoor() %></td>
                <td><%= wifi.getRemars3() %></td>
                <td><%= wifi.getLat() %></td>
                <td><%= wifi.getLnt() %></td>
                <td><%= wifi.getWorkDttm() %></td>
            </tr>
            <%
                            }
                        } else {
            %>
            <tr><td colspan="17">근처 와이파이 정보가 없습니다.</td></tr>
            <%
                        }
                    } catch (NumberFormatException e) {
            %>
            <tr><td colspan="17">위치 정보가 올바르지 않습니다. 다시 입력해주세요.</td></tr>
            <%
                    }
                } else {
            %>
            <tr><td colspan="17">위치 정보를 입력한 후에 조회해주세요.</td></tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
<script>
    window.onload = function () {
        document.getElementById("getLocation").addEventListener("click", function() {
            document.getElementById("lat").value = "37.4811992";
            document.getElementById("lnt").value = "126.8955438";
        });
    };
</script>
</html>
