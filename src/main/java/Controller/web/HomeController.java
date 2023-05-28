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
import DAO.ProductDAO;
import DaoImpl.CartDAOImpl;
import DaoImpl.CategoryDAOImpl;
import DaoImpl.ProductDAOImpl;
import Entity.Category;
import Entity.Product;
import Entity.User;

@WebServlet(urlPatterns = { "/home" })
public class HomeController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ProductDAO product = new ProductDAOImpl();
	CategoryDAO category = new CategoryDAOImpl();
	CartDAO cart = new CartDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("X-Content-Type-Options", "nosniff");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		// Top Product
		List<Product> plist = product.get8Product();
		List<Product> plist3lastest = product.get3LastestProduct();
		List<Product> plist3_6lastest = product.get3_6LastestProduct();
		List<Product> plist3bestseller = product.get3BestSellerProduct();
		List<Product> plist3_6bestseller = product.get3_6BestSellerProduct();
		req.setAttribute("pList", plist);
		req.setAttribute("pList3Lastest", plist3lastest);
		req.setAttribute("pList3_6Lastest", plist3_6lastest);
		req.setAttribute("pList3BestSeller", plist3bestseller);
		req.setAttribute("pList3_6BestSeller", plist3_6bestseller);

		// Category(Header)
		List<Category> clist = category.getAllCategory1();
		req.setAttribute("cList", clist);
		List<Category> clist2 = category.getAllCategory2();
		req.setAttribute("cList2", clist2);

		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");

		if (u != null) {
			int countCart = cart.countCart(cart.getCartIdByUId(u.getId()));
			req.setAttribute("countC", countCart);
		}

		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/web/home.jsp");
		dispatcher.forward(req, resp);
	}
}
