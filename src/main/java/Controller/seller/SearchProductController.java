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

import org.apache.commons.text.StringEscapeUtils;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import DAO.StoreDAO;
import DaoImpl.CategoryDAOImpl;
import DaoImpl.ProductDAOImpl;
import DaoImpl.StoreDAOImpl;
import Entity.Category;
import Entity.Product;
import Entity.User;

@WebServlet(urlPatterns = { "/seller/search" })
public class SearchProductController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ProductDAO product = new ProductDAOImpl();
	CategoryDAO category = new CategoryDAOImpl();
	StoreDAO storeDao = new StoreDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");

		String search = req.getParameter("txt").trim();
		String indexS = StringEscapeUtils.escapeHtml4(req.getParameter("index"));
		if (indexS == null) {
			indexS = "1";
		}

		int index = Integer.parseInt(indexS);

		List<Category> clist2 = category.getAllCategory2();
		req.setAttribute("cList2", clist2);

		if (search != null) {
			//Product
			List<Product> plist = product.pagingSearchByStore(storeDao.GetStoreIdFromUID(u.getId()), search, index);
			req.setAttribute("pList", plist);
			
			int count = product.countSearchByStore(storeDao.GetStoreIdFromUID(u.getId()), search);
	        int size = 10;
	        int endPage = count/size;
	        if(count % size !=0) {
	        	endPage++;
	        }
	        
	        req.setAttribute("endPage", endPage);
	        req.setAttribute("tag", index);
			req.setAttribute("search", search);
		} else {
			//Product
			List<Product> plist = product.GetProductByStoreId(storeDao.GetStoreIdFromUID(u.getId()),index);
			req.setAttribute("pList", plist);
			
			int count = product.countProduct(storeDao.GetStoreIdFromUID(u.getId()));
	        int size = 10;
	        int endPage = count/size;
	        if(count % size !=0) {
	        	endPage++;
	        }
	        
	        req.setAttribute("endPage", endPage);
	        req.setAttribute("tag", index);
			req.setAttribute("search", search);
		}

		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/seller/searchProduct.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");

		String search = req.getParameter("txt").trim();
		String indexS = req.getParameter("index");
		if (indexS == null) {
			indexS = "1";
		}

		int index = Integer.parseInt(indexS);

		List<Category> clist2 = category.getAllCategory2();
		req.setAttribute("cList2", clist2);

		if (search != null) {
			int count = product.countSearchByStore(storeDao.GetStoreIdFromUID(u.getId()), search);
			int size = 10;
			int endPage = count / size;
			if (count % size != 0) {
				endPage++;
			}
			// Product
			List<Product> plist = product.pagingSearchByStore(storeDao.GetStoreIdFromUID(u.getId()), search, index);
			req.setAttribute("pList", plist);

			req.setAttribute("endPage", endPage);
			req.setAttribute("tag", index);

			req.setAttribute("search", search);
		} else {
			int count = product.countProduct(storeDao.GetStoreIdFromUID(u.getId()));
			int size = 10;
			int endPage = count / size;
			if (count % size != 0) {
				endPage++;
			}
			// Product
			List<Product> plist = product.GetProductByStoreId(storeDao.GetStoreIdFromUID(u.getId()), index);
			req.setAttribute("pList", plist);

			req.setAttribute("endPage", endPage);
			req.setAttribute("tag", index);

			req.setAttribute("search", search);
		}

		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/seller/searchProduct.jsp");
		dispatcher.forward(req, resp);
	}

}