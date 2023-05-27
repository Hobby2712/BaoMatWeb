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

@WebServlet(urlPatterns = {"/api/v1/addOrder/*"})
public class AddOrderApi extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	OrderDAO dao = new OrderDAOImpl();
	CartDAO cdao = new CartDAOImpl();
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");
		String name = req.getParameter("name");
		String phone = req.getParameter("phone");
		String address = req.getParameter("address");
		try {
			dao.insertOrder(u.getId(), name, phone, address);
			List <Cart> a = cdao.getAllItemsCart(cdao.getCartIdByUId(u.getId()), 1);
			if(a!= null) {
			for(Cart aitems : a) 
				{
				dao.insertOrderItems(dao.getOrderIdByUId(u.getId()), aitems.getP().getId(), aitems.getCount());
				APIResponse<Cart> response = new APIResponse<>("Thêm Order thành công", false, "cart", aitems);
				OutputStream outputStream = resp.getOutputStream();
			    Gson gson = new Gson();
			    outputStream.write(gson.toJson(response).getBytes());
			    outputStream.flush();
				}		
			
			cdao.deleteAllItemCart(cdao.getCartIdByUId(u.getId()));
			//resp.sendRedirect("order");
			}
			else {
				APIResponse<String> response = new APIResponse<>("Thêm Order thất bại, không có sản phẩm trong giỏ hàng", true);
				OutputStream outputStream = resp.getOutputStream();
			    Gson gson = new Gson();
			    outputStream.write(gson.toJson(response).getBytes());
			    outputStream.flush();
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
