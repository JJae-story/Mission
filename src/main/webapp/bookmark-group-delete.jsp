<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="dao.BookmarkGroupDAO" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
    <title>북마크 그룹 삭제 결과</title>
</head>
<body>
    <%
        String idParam = request.getParameter("id");
        
        if (idParam != null) {
            int id = Integer.parseInt(idParam);

            int result = BookmarkGroupDAO.deleteBookmarkGroupById(id);

            if (result > 0) {
                session.setAttribute("alertMessage", "북마크 그룹을 삭제하였습니다.");
            } else {
                session.setAttribute("alertMessage", "삭제에 실패하였습니다. 다시 시도해주세요.");
            }

            response.sendRedirect("bookmark-group.jsp");
        }
    %>
</body>
</html>
