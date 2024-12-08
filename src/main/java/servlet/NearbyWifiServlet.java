package servlet;

import dao.WifiDAO;
import model.WifiInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("serial")
@WebServlet("/nearby-wifi")
public class NearbyWifiServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            double lat = Double.parseDouble(request.getParameter("lat"));
            double lnt = Double.parseDouble(request.getParameter("lnt"));

            // DB에서 가까운 Wi-Fi 20개 조회
            List<WifiInfo> nearbyWifiList = WifiDAO.getNearbyWifi(lat, lnt);
            request.setAttribute("nearbyWifiList", nearbyWifiList);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "위치 정보를 입력한 후에 조회해주세요.");
        }

        // index.jsp로 전달
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
