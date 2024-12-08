<%@ page import="model.BookmarkGroup" %>
<%@ page import="dao.BookmarkGroupDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
    <title>북마크 그룹 수정</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h1>북마크 그룹 수정</h1>
    
    <a href="index.jsp">홈</a> &#124;
    <a href="history.jsp">위치 히스토리 목록</a> &#124;
    <a href="fetch-wifi">Open API 와이파이 정보 가져오기</a> &#124;
    <a href="bookmark.jsp">북마크 보기</a> &#124;
    <a href="bookmark-group.jsp">북마크 그룹 관리</a>

    <br><br>
    
    <%
	    request.setCharacterEncoding("UTF-8");
	%>

    <% 
        String idParam = request.getParameter("id");
        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            BookmarkGroup group = BookmarkGroupDAO.getBookmarkGroupById(id);
            if (group != null) {
    %>
                <form action="bookmark-group-update.jsp" method="post" accept-charset="UTF-8">
                    <input type="hidden" name="id" value="<%= group.getId() %>" />
                    <table>
                        <tr>
                            <td><label for="name">북마크 이름</label></td>
                            <td><input type="text" id="name" name="name" value="<%= group.getName() %>" required></td>
                        </tr>
                        <tr>
                            <td><label for="orderNum">순서</label></td>
                            <td><input type="text" id="orderNum" name="orderNum" value="<%= group.getOrderNum() %>" required></td>
                        </tr>
                        <tr>
                            <td colspan="2" style="text-align: center;">
                            	<a href="bookmark-group.jsp">돌아가기</a> |
                                <button type="submit">수정</button>
                            </td>
                        </tr>
                    </table>
                </form>
    <% 
            } else {
    %>
                <p>해당 북마크 그룹을 찾을 수 없습니다.</p>
    <% 
            }
        } else {
    %>
            <p>잘못된 접근입니다. 북마크 그룹 ID가 필요합니다.</p>
    <% 
        }
    %>
</body>
</html>
