<%@page import="model.WifiInfo"%>
<%@page import="dao.WifiDAO"%>
<%@page import="dao.BookmarkGroupDAO"%>
<%@page import="model.BookmarkGroup"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>와이파이 상세 정보</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h1>와이파이 상세 정보</h1>

    <a href="index.jsp">홈</a> &#124;
    <a href="history.jsp">위치 히스토리 목록</a> &#124;
    <a href="fetch-wifi">Open API 와이파이 정보 가져오기</a> &#124;
    <a href="bookmark.jsp">북마크 보기</a> &#124;
    <a href="bookmark-group.jsp">북마크 그룹 관리</a><br>

    <%
        String latStr = request.getParameter("lat");
        String lntStr = request.getParameter("lnt");

        double lat = 37.4811992;
        double lnt = 126.8955438;

        if (latStr != null && lntStr != null) {
            try {
                lat = Double.parseDouble(latStr);
                lnt = Double.parseDouble(lntStr);
            } catch (NumberFormatException e) {
                out.println("<p>위도와 경도가 잘못된 형식입니다. 다시 입력해주세요.</p>");
            }
        }

        String mgrNo = request.getParameter("mgrNo");
        WifiInfo wifi = WifiDAO.getWifiInfoById(mgrNo, lat, lnt);
        
        if (wifi != null) {
            String formattedDistance = String.format("%.4f", wifi.getDistance());  // 거리 포맷팅
    %>

	<form action="addBookmark" method="post" id="bookmarkForm" onsubmit="return validateForm()">
    <input type="hidden" name="wifiId" value="<%= wifi.getMgrNo() %>">
    <input type="hidden" name="wifiName" value="<%= wifi.getMainNm() %>">
    <input type="hidden" name="createdAt" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()) %>">
    <input type="hidden" name="groupId" id="groupId">
    
    <select name="groupName" id="groupName" onchange="updateGroupId()">
        <%
            List<BookmarkGroup> bookmarkGroups = BookmarkGroupDAO.getAllBookmarkGroups();
            if (bookmarkGroups != null && !bookmarkGroups.isEmpty()) {
                for (BookmarkGroup group : bookmarkGroups) {
        %>
        <option value="<%= group.getName() %>"><%= group.getName() %></option>
        <%
                }
            } else {
        %>
        <option value="">북마크 그룹 선택</option>
        <%
            }
        %>
    </select>

    <button type="submit">북마크 추가</button> 
</form>

    <table>
		<tr><td>거리 (Km)</td><td><%= formattedDistance %></td></tr>
        <tr><td>관리번호</td><td><%= wifi.getMgrNo() %></td></tr>
        <tr><td>자치구</td><td><%= wifi.getWrdofc() %></td></tr>
        <tr><td>와이파이명</td><td><a href="detail.jsp?mgrNo=<%= wifi.getMgrNo() %>"><%= wifi.getMainNm() %></a></td></tr>
        <tr><td>도로명주소</td><td><%= wifi.getAdres1() %></td></tr>
        <tr><td>상세주소</td><td><%= wifi.getAdres2() %></td></tr>
        <tr><td>설치위치(층)</td><td><%= wifi.getInstlFloor() %></td></tr>
        <tr><td>설치유형</td><td><%= wifi.getInstlTy() %></td></tr>
        <tr><td>설치기관</td><td><%= wifi.getInstlMby() %></td></tr>
        <tr><td>서비스구분</td><td><%= wifi.getSvcSe() %></td></tr>
        <tr><td>망종류</td><td><%= wifi.getCmcwr() %></td></tr>
        <tr><td>설치년도</td><td><%= wifi.getCnstcYear() %></td></tr>
        <tr><td>실내외구분</td><td><%= wifi.getInoutDoor() %></td></tr>
        <tr><td>WIFI접속환경</td><td><%= wifi.getRemars3() %></td></tr>
        <tr><td>X좌표</td><td><%= wifi.getLat() %></td></tr>
        <tr><td>Y좌표</td><td><%= wifi.getLnt() %></td></tr>
        <tr><td>작업일자</td><td><%= wifi.getWorkDttm() %></td></tr>
    </table>

    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
    <script type="text/javascript">
        alert("<%= message %>");
        window.location.href = "bookmark.jsp"; // 북마크 페이지로 이동
    </script>
    <%
        }
        } else {
    %>
        <p>와이파이 정보를 찾을 수 없습니다.</p>
    <%
        }
    %>
</body>
<script>
    // 선택된 그룹 id 값을 숨겨진 input 필드에 설정
    function updateGroupId() {
        var selectedGroup = document.getElementById("groupName");
        var selectedGroupId = selectedGroup.value;
        document.getElementById("groupId").value = selectedGroupId;
    }
</script>
</html>
