<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<body>
    <%
        Integer savedCount = (Integer) request.getAttribute("savedCount");
        if (savedCount != null) {
    %>
    <div style="text-align: center;">
	    <h1 style="margin: 20px"><strong><%= savedCount %></strong>건의 데이터를 성공적으로 저장했습니다.</h1>
	    <%
	        } else {
	    %>
	        <p>저장된 데이터가 없습니다.</p>
	    <%
	        }
	    %>
	    <a href="index.jsp">홈으로 이동</a>
    </div>
</body>
</html>
