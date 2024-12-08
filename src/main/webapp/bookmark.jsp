<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
    <title>북마크 보기</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h1>북마크 보기</h1>

    <a href="index.jsp">홈</a> &#124;
    <a href="history.jsp">위치 히스토리 목록</a> &#124;
    <a href="fetch-wifi">Open API 와이파이 정보 가져오기</a> &#124;
    <a href="bookmark.jsp">북마크 보기</a> &#124;
    <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    
    <c:if test="${not empty sessionScope.alertMessage}">
        <script type="text/javascript">
            alert("${sessionScope.alertMessage}");
        </script>
        <c:set var="alertMessage" value="${sessionScope.alertMessage}" />
        <c:remove var="sessionScope.alertMessage"/>
    </c:if>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>북마크 이름</th>
                <th>와이파이명</th>
                <th>등록일자</th>
                <th>비고</th>
            </tr>
        </thead>
        <tbody>
            <c:if test="${not empty bookmarks}">
                <c:forEach var="bookmark" items="${bookmarks}">
                    <tr>
                        <td>${bookmark.id}</td>
                        <td>${bookmark.groupName}</td>
                        <td>${bookmark.wifiName}</td>
                        <td>${bookmark.createdAt}</td>
                        <td>
                            <a href="bookmark-delete.jsp?id=${bookmark.id}">삭제</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>

            <c:if test="${empty bookmarks}">
                <tr>
                    <td colspan="5" style="text-align: center;">정보가 존재하지 않습니다.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</body>
</body>
</html>
