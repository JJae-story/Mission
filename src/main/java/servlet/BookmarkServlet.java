package servlet;

import dao.BookmarkDAO;
import model.Bookmark;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("serial")
@WebServlet("/bookmark")
public class BookmarkServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 북마크 목록을 조회
        List<Bookmark> bookmarks = BookmarkDAO.getAllBookmarks();
        
        // 데이터를 request에 담기
        request.setAttribute("bookmarks", bookmarks);
        
        // bookmark.jsp로 포워딩
        request.getRequestDispatcher("bookmark.jsp").forward(request, response);
    }
}

