<%@ page import="dao.BookmarkDAO" %>
<%@ page import="model.Bookmark" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
    <title>북마크 삭제 확인</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h1>북마크 삭제 확인</h1>

    <a href="index.jsp">홈</a> &#124;
    <a href="history.jsp">위치 히스토리 목록</a> &#124;
    <a href="fetch-wifi">Open API 와이파이 정보 가져오기</a> &#124;
    <a href="bookmark.jsp">북마크 보기</a> &#124;
    <a href="bookmark-group.jsp">북마크 그룹 관리</a><br>

    <h2>북마크를 삭제하시겠습니까?</h2>
    
    <%
        String bookmarkId = request.getParameter("id");
        if (bookmarkId != null && !bookmarkId.isEmpty()) {
            int id = Integer.parseInt(bookmarkId);
            BookmarkDAO bookmarkDAO = new BookmarkDAO();
            Bookmark bookmark = bookmarkDAO.getBookmarkById(id);

            if (bookmark != null) {
    %>

    <table>
        <tr>
            <td>북마크 이름:</td>
            <td><%= bookmark.getGroupName() %></td>
        </tr>
        <tr>
            <td>와이파이명:</td>
            <td><%= bookmark.getWifiName() %></td>
        </tr>
        <tr>
            <td>등록일자:</td>
            <td><%= bookmark.getCreatedAt() %></td>
        </tr>

        <tr>
            <td colspan="2" style="text-align: center;">
                <div style="display: flex; justify-content: center; align-items: center; gap: 10px;">
                    <a href="bookmark.jsp">돌아가기</a> |
                    <form action="deleteBookmark" method="get">
                        <input type="hidden" name="id" value="<%= bookmark.getId() %>" />
                        <button type="submit">삭제</button>
                    </form>
                </div>
            </td>
        </tr>
    </table>

    <%
            } else {
    %>
        <p>유효하지 않은 북마크입니다.</p>
    <%
            }
        } else {
    %>
        <p>북마크 ID가 제공되지 않았습니다.</p>
    <%
        }
    %>
    
</body>
</html>
