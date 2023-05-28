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
import DaoImpl.CartDAOImpl;
import DaoImpl.CategoryDAOImpl;
import Entity.Cart;
import Entity.Category;
import Entity.Product;
import Entity.User;

@WebServlet(urlPatterns = { "/checkout" })
public class CheckOutController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CategoryDAO category = new CategoryDAOImpl();
	CartDAO cart = new CartDAOImpl();
	Product p = new Product();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("X-Content-Type-Options", "nosniff");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");

		List<Cart> cartlist = cart.getAllItemsCart(cart.getCartIdByUId(u.getId()), 1);
		req.setAttribute("cartList", cartlist);
		req.setAttribute("totalP", p.tienTe(cart.getTotalCart(cart.getCartIdByUId(u.getId()))));

		// Category(Header)
		List<Category> clist = category.getAllCategory1();
		req.setAttribute("cList", clist);
		List<Category> clist2 = category.getAllCategory2();
		req.setAttribute("cList2", clist2);

		if (u != null) {
			int countCart = cart.countCart(cart.getCartIdByUId(u.getId()));
			req.setAttribute("countC", countCart);
		}

		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/web/checkout.jsp");
		dispatcher.forward(req, resp);
	}
}
