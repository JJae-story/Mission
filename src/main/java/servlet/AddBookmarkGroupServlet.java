package servlet;

import dao.BookmarkGroupDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet("/addBookmarkGroup")
public class AddBookmarkGroupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	
        String name = request.getParameter("name");
        String orderNumStr = request.getParameter("orderNum");
        int orderNum = Integer.parseInt(orderNumStr);

        // 북마크 그룹 추가 처리
        int result = BookmarkGroupDAO.addBookmarkGroup(name, orderNum);

        if (result > 0) {
            request.getSession().setAttribute("alertMessage", "북마크 그룹이 추가되었습니다.");
        } else {
            request.getSession().setAttribute("alertMessage", "북마크 그룹 추가 실패.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("bookmark-group-add.jsp");
        dispatcher.forward(request, response);
    }
}
