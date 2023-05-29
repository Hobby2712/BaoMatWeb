package Controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ThongKeDAO;
import DaoImpl.ThongKeDAOImpl;
import Entity.Chart;
import Entity.ThongKe;
import Util.Constant;

@WebServlet(urlPatterns = { "/admin/homeAdmin" })
public class HomeAdminController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ThongKeDAO thongke = new ThongKeDAOImpl();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cookieHeader = String.format("JSESSIONID=%s; %s", req.getSession().getId(), Constant.sameSite);
		resp.setHeader("Set-Cookie", cookieHeader);
		resp.setHeader("X-Content-Type-Options", "nosniff");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		
		String index = req.getParameter("index");
		String year = req.getParameter("year");

		if (year == null) {
			year = thongke.getLastedYear();
		}
		
		if(index == null){
			index = "1";
		}
		
		int count = thongke.countThongKe(Integer.parseInt(year));
        int size = 10;
        int endPage = count/size;
        if(count % size !=0) {
        	endPage++;
        }
		List<String> yearSelect = thongke.getYear();
		List<Chart> tlist = thongke.getAllThongKe(Integer.parseInt(year), Integer.parseInt(index));
		req.setAttribute("tList", tlist);
		List<Chart> a = thongke.getChart(Integer.parseInt(year));
		
		req.setAttribute("yearSelect", yearSelect);
		req.setAttribute("endPage", endPage);
        req.setAttribute("tag", index);
		req.setAttribute("yearIndex", Integer.parseInt(year));
		req.setAttribute("chart", a);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/homeAdmin.jsp");
		dispatcher.forward(req, resp);
	}

}
