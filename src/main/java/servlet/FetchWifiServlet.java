package servlet;

import service.APIService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet("/fetch-wifi")
public class FetchWifiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
            int savedCount = APIService.getPublicWifiJson();

            request.setAttribute("savedCount", savedCount);

            request.getRequestDispatcher("wifi-info.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "와이파이 데이터를 처리하는 중 오류 발생");
        }
    }
}
