<%@ page import="dao.BookmarkGroupDAO" %>
<%@ page import="model.BookmarkGroup" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
    <title>북마크 그룹 수정 결과</title>
</head>
<body>
    <%
        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        String orderNumParam = request.getParameter("orderNum");
        
        if (idParam != null && name != null && orderNumParam != null) {
            int id = Integer.parseInt(idParam);
            int orderNum = Integer.parseInt(orderNumParam);
            
            int result = BookmarkGroupDAO.updateBookmarkGroup(id, name, orderNum);

            if (result > 0) {
                session.setAttribute("alertMessage", "북마크 그룹 정보를 수정하였습니다.");
                response.sendRedirect("bookmark-group.jsp");
            } else {
                out.println("수정 실패. 다시 시도해주세요.");
            }
        }
    %>
</body>
</html>
