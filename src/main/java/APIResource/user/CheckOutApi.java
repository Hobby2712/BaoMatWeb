package APIResource.user;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import DAO.CartDAO;
import DAO.CategoryDAO;
import DAO.OrderDAO;
import DAO.ProductDAO;
import DaoImpl.CartDAOImpl;
import DaoImpl.CategoryDAOImpl;
import DaoImpl.OrderDAOImpl;
import DaoImpl.ProductDAOImpl;
import Entity.Cart;
import Entity.Category;
import Entity.Order;
import Entity.OrderDetails;
import Entity.Product;
import Entity.User;
import Entity.api.APIResponse;
import Util.Constant;

@WebServlet(urlPatterns = {"/api/v1/checkout/*"})
public class CheckOutApi extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CategoryDAO category = new CategoryDAOImpl();
	CartDAO cart = new CartDAOImpl();
	Product p = new Product();
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");
		
		List<Cart> cartlist = cart.getAllItemsCart(cart.getCartIdByUId(u.getId()), 1);
		
		req.setAttribute("cartList", cartlist);
		req.setAttribute("totalP", p.tienTe(cart.getTotalCart(cart.getCartIdByUId(u.getId()))));
		
		if (u != null) {
			int countCart = cart.countCart(cart.getCartIdByUId(u.getId()));
			req.setAttribute("countC", countCart);
		}
	}
}
