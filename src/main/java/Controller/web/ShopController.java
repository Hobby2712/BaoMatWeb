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

import org.apache.commons.text.StringEscapeUtils;

import DAO.CartDAO;
import DAO.CategoryDAO;
import DAO.ProductDAO;
import DaoImpl.CartDAOImpl;
import DaoImpl.CategoryDAOImpl;
import DaoImpl.ProductDAOImpl;
import Entity.Category;
import Entity.Product;
import Entity.User;

@WebServlet(urlPatterns = {"/shop"})
public class ShopController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ProductDAO product = new ProductDAOImpl();
	CategoryDAO category = new CategoryDAOImpl();
	CartDAO cart = new CartDAOImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		
<<<<<<< HEAD
		// trước 
		//int index = Integer.parseInt(req.getParameter("index"));
		//	sau
		int maxLength = 200;
		int index = 1;
		String strIndex = req.getParameter("index");
		try {
			if(strIndex.length()>maxLength) {
				index = 1;
			}
			index = Integer.parseInt(strIndex);
			
		}catch(NumberFormatException e){
			 System.out.println("Error: " + e.getMessage());
			 index = 1;
		}
		
		
=======
		int index = Integer.parseInt(StringEscapeUtils.escapeHtml4(req.getParameter("index")));
>>>>>>> 1f4540f (Done XSS full)
		
		//Product
		List<Product> plist = product.getAllProduct(index);
		List<Product> plist3lastest = product.get3LastestProduct();
		List<Product> plist3_6lastest = product.get3_6LastestProduct();
		req.setAttribute("pList", plist);
		req.setAttribute("pList3Lastest", plist3lastest);
		req.setAttribute("pList3_6Lastest", plist3_6lastest);
		
		//Category
		List<Category> clist = category.getAllCategory1();
		req.setAttribute("cList", clist);
		List<Category> clist2 = category.getAllCategory2();
		req.setAttribute("cList2", clist2);
		
		int count = product.countProduct();
        int size = 9;
        int endPage = count/size;
        if(count % size !=0) {
        	endPage++;
        }
        
        HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");

		if (u != null) {
			int countCart = cart.countCart(cart.getCartIdByUId(u.getId()));
			req.setAttribute("countC", countCart);
		}
        
        req.setAttribute("endPage", endPage);
        req.setAttribute("tag", index);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/web/shop-grid.jsp");
		dispatcher.forward(req, resp);
	}
}
