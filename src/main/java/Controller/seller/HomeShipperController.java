package Controller.seller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.OrderDAO;
import DaoImpl.OrderDAOImpl;
import Entity.OrderDetails;
import Util.Constant;

@WebServlet(urlPatterns = { "/shipper/homeShipper" })
public class HomeShipperController extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	OrderDAO order = new OrderDAOImpl();
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cookieHeader = String.format("JSESSIONID=%s; %s", req.getSession().getId(), Constant.sameSite);
		resp.setHeader("Set-Cookie", cookieHeader);
		resp.setHeader("X-Content-Type-Options", "nosniff");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		
		String status = req.getParameter("status");
		
		if(status == null){
			List<OrderDetails> olist = order.getAllOrderShipper();
			req.setAttribute("oList", olist);
		}
		else {
			List<OrderDetails> olist = order.getOrderByStatus(Integer.parseInt(status));
			req.setAttribute("oList", olist);
		}
	
		RequestDispatcher dispatcher = req.getRequestDispatcher("/common/shipper.jsp");
		dispatcher.forward(req, resp);
	}
	
}
