<%@ page import="java.util.List" %>
<%@ page import="model.BookmarkGroup" %>
<%@ page import="dao.BookmarkGroupDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h1>북마크 그룹 관리</h1>
    
    <a href="index.jsp">홈</a> &#124;
    <a href="history.jsp">위치 히스토리 목록</a> &#124;
    <a href="fetch-wifi">Open API 와이파이 정보 가져오기</a> &#124;
    <a href="bookmark.jsp">북마크 보기</a> &#124;
    <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    
    <div>
        <a href="bookmark-group-add.jsp"><button>북마크 그룹 추가</button></a>
    </div>

    <%
        String alertMessage = (String) session.getAttribute("alertMessage");
        if (alertMessage != null) {
    %>
        <script>alert('<%= alertMessage %>');</script>
    <%
            session.removeAttribute("alertMessage");
        }
    %>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>북마크 이름</th>
                <th>순서</th>
                <th>등록일자</th>
                <th>수정일자</th>
                <th>비고</th>
            </tr>
    	</thead>
	    <tbody>
	        <%
	            List<BookmarkGroup> bookmarkGroups = BookmarkGroupDAO.getAllBookmarkGroups();
	
	            if (bookmarkGroups != null && !bookmarkGroups.isEmpty()) {
	                for (BookmarkGroup group : bookmarkGroups) {
	        %>
	                    <tr>
	                        <td><%= group.getId() %></td>
	                        <td><%= group.getName() %></td>
	                        <td><%= group.getOrderNum() %></td>
	                        <td><%= group.getCreatedAt() %></td>
	                        <td><%= group.getUpdatedAt() != null ? group.getUpdatedAt() : "" %></td>
	                        <td>
	                            <a href="bookmark-group-edit.jsp?id=<%= group.getId() %>">수정</a>
	                            <a href="bookmark-group-delete.jsp?id=<%= group.getId() %>">삭제</a>
	                        </td>
	                    </tr>
	        <%
	                }
	            } else {
	        %>
	                <tr>
	                    <td colspan="6">정보가 존재하지 않습니다.</td>
	                </tr>
	        <%
	            }
	        %>
	    </tbody>
	</table>
</body>
</html>
