package Controller.seller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.StoreDAO;
import DAO.ThongKeDAO;
import DaoImpl.StoreDAOImpl;
import DaoImpl.ThongKeDAOImpl;
import Entity.Chart;
import Entity.ThongKe;
import Entity.User;
import Util.Constant;

@WebServlet(urlPatterns = { "/seller/homeSeller" })
public class HomeSellerController extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ThongKeDAO thongke = new ThongKeDAOImpl();
	StoreDAO storeDao = new StoreDAOImpl();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cookieHeader = String.format("JSESSIONID=%s; %s", req.getSession().getId(), Constant.sameSite);
		resp.setHeader("Set-Cookie", cookieHeader);
		resp.setHeader("X-Content-Type-Options", "nosniff");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");
		
		String index = req.getParameter("index");
		int storeId = storeDao.GetStoreIdFromUID(u.getId());
		String year = req.getParameter("year");
		
		if(year == null){
			year = thongke.getLastedYearByStore(storeId);
		}
		
		if(index == null){
			index = "1";
		}
		
		int count = thongke.countThongKeByStore(Integer.parseInt(year), storeId);
        int size = 10;
        int endPage = count/size;
        if(count % size !=0) {
        	endPage++;
        }
		
		List<ThongKe> tlist = thongke.getAllThongKeByStore(Integer.parseInt(year), storeId, Integer.parseInt(index));
		req.setAttribute("tList", tlist);
		List<Chart> a = thongke.getChartByStore(Integer.parseInt(year), storeId);
		List<String> yearSelect = thongke.getYearByStore(storeId);
		
		req.setAttribute("yearSelect", yearSelect);
		req.setAttribute("endPage", endPage);
        req.setAttribute("tag", index);
		req.setAttribute("yearIndex", Integer.parseInt(year));
		req.setAttribute("chart", a);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/seller/homeSeller.jsp");
		dispatcher.forward(req, resp);
	}
	
}
