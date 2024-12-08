<%@page import="model.History"%>
<%@page import="dao.HistoryDAO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>위치 히스토리 목록</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h1>위치 히스토리 목록</h1>

    <a href="index.jsp">홈</a> &#124;
    <a href="history.jsp">위치 히스토리 목록</a> &#124;
    <a href="fetch-wifi">Open API 와이파이 정보 가져오기</a> &#124;
    <a href="bookmark.jsp">북마크 보기</a> &#124;
    <a href="bookmark-group.jsp">북마크 그룹 관리</a>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>X좌표</th>
                <th>Y좌표</th>
                <th>조회일자</th>
                <th>비고</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<History> historyList = HistoryDAO.getAllHistory();
                if (historyList != null && !historyList.isEmpty()) {
                    for (History history : historyList) {
            %>
            <tr>
                <td><%= history.getId() %></td>
                <td><%= history.getLat() %></td>
                <td><%= history.getLnt() %></td>
                <td><%= history.getSearchDttm() %></td>
                <td>
                    <form method="get" action="delete-history">
				    	<input type="hidden" name="id" value="<%= history.getId() %>">
				    	<button type="submit" class="delete-button">삭제</button>
					</form>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="5">저장된 히스토리가 없습니다.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
