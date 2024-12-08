<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
    <title>북마크 그룹 추가</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h1>북마크 그룹 추가</h1>

    <%
        String alertMessage = (String) session.getAttribute("alertMessage");
        if (alertMessage != null) {
    %>
        <script>
            alert('<%= alertMessage %>');
            location.href = "bookmark-group.jsp";
        </script>
    <%
            session.removeAttribute("alertMessage");
        }
    %>

    <form action="addBookmarkGroup" method="post">
        <table>
            <tr>
                <td><label for="name">북마크 이름</label></td>
                <td><input type="text" id="name" name="name" required></td>
            </tr>
            <tr>
                <td><label for="orderNum">순서</label></td>
                <td><input type="text" id="orderNum" name="orderNum" required></td>
            </tr>
            <tr>
            	<td colspan="2" style="text-align: center;">
            		<button type="submit">추가</button>
        			<a href="bookmark-group.jsp">돌아가기</a>
        		</td>
        	</tr>
        </table>
    </form>
</body>
</html>
