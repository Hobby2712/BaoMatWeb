package Controller.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.CartDAO;
import DAO.CategoryDAO;
import DAO.OrderDAO;
import DaoImpl.CartDAOImpl;
import DaoImpl.CategoryDAOImpl;
import DaoImpl.OrderDAOImpl;
import Entity.Category;
import Entity.OrderDetails;
import Entity.User;

@WebServlet(urlPatterns = {"/order"})
public class OrderController extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	OrderDAO order = new OrderDAOImpl();
	CategoryDAO category = new CategoryDAOImpl();
	CartDAO cart = new CartDAOImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("X-Content-Type-Options", "nosniff");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");
		
		if (u != null) {
			List<OrderDetails> olist = order.getAllItemsOrder(u.getId());
			req.setAttribute("oList", olist);
			//Category(Header)
			List<Category> clist = category.getAllCategory1();
			req.setAttribute("cList", clist);
			List<Category> clist2 = category.getAllCategory2();
			req.setAttribute("cList2", clist2);
			int countCart = cart.countCart(cart.getCartIdByUId(u.getId()));
			req.setAttribute("countC", countCart);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/web/order.jsp");
			dispatcher.forward(req, resp);
		}
		else {
			req.setAttribute("mess", "Đăng nhập để mua hàng");
			RequestDispatcher dispatcher = req.getRequestDispatcher("/loginAccount");
			dispatcher.forward(req, resp);
		}
		
	}
}
