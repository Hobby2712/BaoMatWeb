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

import DAO.OrderDAO;
import DAO.StoreDAO;
import DaoImpl.OrderDAOImpl;
import DaoImpl.StoreDAOImpl;
import Entity.OrderDetails;
import Entity.User;

@WebServlet(urlPatterns = {"/seller/order"})
public class OrderSellerController extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	OrderDAO order = new OrderDAOImpl();
	StoreDAO storeDao = new StoreDAOImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("X-Content-Type-Options", "nosniff");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");
		List<OrderDetails> statusCount = order.getCountOrderStatus(storeDao.GetStoreIdFromUID(u.getId()));
		int count_all = 0;
		for(OrderDetails a: statusCount) {
			count_all += a.getCount();
		}
		
		List<OrderDetails> olist = order.getAllOrder(storeDao.GetStoreIdFromUID(u.getId()));
		req.setAttribute("oList", olist);
		req.setAttribute("sList", statusCount);
		req.setAttribute("count_all", count_all);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/seller/orderSeller.jsp");
		dispatcher.forward(req, resp);
	}
}

