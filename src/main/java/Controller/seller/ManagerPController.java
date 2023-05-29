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

import DAO.CategoryDAO;
import DAO.ProductDAO;
import DAO.StoreDAO;
import DaoImpl.CategoryDAOImpl;
import DaoImpl.ProductDAOImpl;
import DaoImpl.StoreDAOImpl;
import Entity.Category;
import Entity.Product;
import Entity.User;
import Util.Constant;

@WebServlet(urlPatterns = {"/seller/managerP"})
public class ManagerPController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ProductDAO product = new ProductDAOImpl();
	CategoryDAO category = new CategoryDAOImpl();
	StoreDAO storeDao = new StoreDAOImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cookieHeader = String.format("JSESSIONID=%s; %s", req.getSession().getId(), Constant.sameSite);
		resp.setHeader("Set-Cookie", cookieHeader);
		resp.setHeader("X-Content-Type-Options", "nosniff");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");
		
		String indexS= req.getParameter("index");
		if(indexS==null) {
			indexS="1";
		}
		int index = Integer.parseInt(indexS);
		//Product
		List<Product> plist = product.GetProductByStoreId(storeDao.GetStoreIdFromUID(u.getId()),index);
		req.setAttribute("pList", plist);
		List<Category> clist2 = category.getAllCategory2();
		req.setAttribute("cList2", clist2);
		
		int count = product.countProduct(storeDao.GetStoreIdFromUID(u.getId()));
        int size = 10;
        int endPage = count/size;
        if(count % size !=0) {
        	endPage++;
        }
        
        req.setAttribute("endPage", endPage);
        req.setAttribute("tag", index);
        
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/seller/manager-product.jsp");
		dispatcher.forward(req, resp);
	}
}
