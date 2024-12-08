package servlet;

import dao.BookmarkDAO;
import model.Bookmark;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("serial")
@WebServlet("/addBookmark")
public class AddBookmarkServlet extends HttpServlet {

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 요청 인코딩을 UTF-8로 설정
        request.setCharacterEncoding("UTF-8");

        // 파라미터 받기
        String groupName = request.getParameter("groupName");
        String wifiName = request.getParameter("wifiName");
        String createdAt = request.getParameter("createdAt");
        String remarks = request.getParameter("remarks");

        // 북마크 객체 생성
        Bookmark bookmark = new Bookmark();
        
        bookmark.setGroupName(groupName);    
        bookmark.setWifiName(wifiName);     
        bookmark.setCreatedAt(createdAt);
        bookmark.setRemarks(remarks);       

        // 북마크 추가
        int result = BookmarkDAO.addBookmark(bookmark);

        // 결과에 따른 메시지 설정
        HttpSession session = request.getSession();
        if (result > 0) {
            session.setAttribute("alertMessage", "북마크가 추가되었습니다.");
        } else {
            session.setAttribute("alertMessage", "북마크 추가에 실패했습니다.");
        }

        // 북마크 목록을 다시 받아서 request에 저장
        List<Bookmark> bookmarks = BookmarkDAO.getAllBookmarks();
        request.setAttribute("bookmarks", bookmarks);

        // bookmark.jsp로 포워딩
        request.getRequestDispatcher("bookmark.jsp").forward(request, response);
    }
}
