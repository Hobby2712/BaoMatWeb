package Controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import DAO.StoreDAO;
import DaoImpl.CategoryDAOImpl;
import DaoImpl.ProductDAOImpl;
import DaoImpl.StoreDAOImpl;
import Entity.Product;
import Util.Constant;
import Util.CsrfTokenUtil;

@WebServlet(urlPatterns = { "/admin/searchP" })
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
		String cookieHeader = String.format("JSESSIONID=%s; %s", req.getSession().getId(), Constant.sameSite);
		resp.setHeader("Set-Cookie", cookieHeader);
		resp.setHeader("X-Content-Type-Options", "nosniff");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		
		String csrfToken = req.getParameter("csrf_token");
	    System.out.println(csrfToken);
		if (csrfToken == null || !csrfToken.equals(req.getSession().getAttribute("csrf_token"))) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.getWriter().write("Invalid CSRF token");
			//System.out.println(request.getSession().getAttribute("csrf_token"));
		    return;
		}
		
		//Tạo token mới lên session
        csrfToken = CsrfTokenUtil.generateCsrfToken();
        req.getSession().setAttribute("csrf_token", csrfToken);
		
		String search = StringEscapeUtils.escapeHtml4(req.getParameter("txt").trim());
		String indexS = StringEscapeUtils.escapeHtml4(req.getParameter("index"));
		if (indexS == null) {
			indexS = "1";
		}

		int index = Integer.parseInt(indexS);

		if (search != null) {
			//Product
			List<Product> plist = product.pagingSearch(search, index);
			req.setAttribute("pList", plist);
			
			int count = product.countSearch(search);
	        int size = 9;
	        int endPage = count/size;
	        if(count % size !=0) {
	        	endPage++;
	        }
	        
	        req.setAttribute("endPage", endPage);
	        req.setAttribute("tag", index);
			req.setAttribute("search", search);
		} else {
			//Product
			List<Product> plist = product.getAllProduct(index);
			req.setAttribute("pList", plist);
			
			int count = product.countProduct();
	        int size = 9;
	        int endPage = count/size;
	        if(count % size !=0) {
	        	endPage++;
	        }
	        
	        req.setAttribute("endPage", endPage);
	        req.setAttribute("tag", index);
			req.setAttribute("search", search);
		}

		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/SearchProduct.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cookieHeader = String.format("JSESSIONID=%s; %s", req.getSession().getId(), Constant.sameSite);
		resp.setHeader("Set-Cookie", cookieHeader);
		resp.setHeader("X-Content-Type-Options", "nosniff");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		
		String search = req.getParameter("txt").trim();
		String indexS = req.getParameter("index");
		if (indexS == null) {
			indexS = "1";
		}

		int index = Integer.parseInt(indexS);

		if (search != null) {
			//Product
			List<Product> plist = product.pagingSearch(search, index);
			req.setAttribute("pList", plist);
			
			int count = product.countSearch(search);
	        int size = 9;
	        int endPage = count/size;
	        if(count % size !=0) {
	        	endPage++;
	        }
	        
	        req.setAttribute("endPage", endPage);
	        req.setAttribute("tag", index);
			req.setAttribute("search", search);
		} else {
			//Product
			List<Product> plist = product.getAllProduct(index);
			req.setAttribute("pList", plist);
			
			int count = product.countProduct();
	        int size = 9;
	        int endPage = count/size;
	        if(count % size !=0) {
	        	endPage++;
	        }
	        
	        req.setAttribute("endPage", endPage);
	        req.setAttribute("tag", index);
			req.setAttribute("search", search);
		}

		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/SearchProduct.jsp");
		dispatcher.forward(req, resp);
	}

}