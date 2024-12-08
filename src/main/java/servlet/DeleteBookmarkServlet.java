package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BookmarkDAO;
import model.Bookmark;

@SuppressWarnings("serial")
@WebServlet("/deleteBookmark")
public class DeleteBookmarkServlet extends HttpServlet {
	@SuppressWarnings("static-access")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookmarkIdParam = request.getParameter("id");

        if (bookmarkIdParam != null && !bookmarkIdParam.isEmpty()) {
            try {
                int bookmarkId = Integer.parseInt(bookmarkIdParam);
                BookmarkDAO bookmarkDAO = new BookmarkDAO();
                boolean isDeleted = bookmarkDAO.deleteBookmark(bookmarkId); // 북마크 삭제

                HttpSession session = request.getSession();
                if (isDeleted) {
                    session.setAttribute("alertMessage", "북마크가 삭제되었습니다.");
                } else {
                    session.setAttribute("alertMessage", "삭제에 실패했습니다.");
                }

                List<Bookmark> bookmarks = bookmarkDAO.getAllBookmarks(); // 모든 북마크 목록을 조회
                request.setAttribute("bookmarks", bookmarks);

                request.getRequestDispatcher("bookmark.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect("bookmark.jsp");
            }
        } else {
            response.sendRedirect("bookmark.jsp");
        }
    }
}
