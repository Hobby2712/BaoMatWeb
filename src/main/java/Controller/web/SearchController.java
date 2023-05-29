package Controller.web;

import java.io.IOException;
import java.util.List;

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
import Util.Constant;
import Util.CsrfTokenUtil;

@WebServlet(urlPatterns = {"/search"})
public class SearchController extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CartDAO cart = new CartDAOImpl();
	ProductDAO product = new ProductDAOImpl();
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		String cookieHeader = String.format("JSESSIONID=%s; %s", req.getSession().getId(), Constant.sameSite);
		resp.setHeader("Set-Cookie", cookieHeader);
		resp.setHeader("X-Content-Type-Options", "nosniff");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		// Lấy token từ request header
	    String csrfToken = req.getParameter("csrf_token");
	    System.out.println(csrfToken);
		if (csrfToken == null || !csrfToken.equals(req.getSession().getAttribute("csrf_token"))) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.getWriter().write("Invalid CSRF token");
			System.out.println(req.getSession().getAttribute("csrf_token"));
		    return;
		}
		
        String txtSearch = req.getParameter("txt");
        String indexS = StringEscapeUtils.escapeHtml4(req.getParameter("index"));
        if(indexS == null) {
        	indexS="1";
        }
        int index = Integer.parseInt(indexS);
        
        ProductDAO dao1 = new ProductDAOImpl();
        CategoryDAO dao2 = new CategoryDAOImpl();
        //Tạo token mới lên session
        csrfToken = CsrfTokenUtil.generateCsrfToken();
        req.getSession().setAttribute("csrf_token", csrfToken);
        
        List<Category> listC = dao2.getAllCategory1();
        List<Category> clist2 = dao2.getAllCategory2();
		req.setAttribute("cList2", clist2);
        
		
		List<Product> plist3lastest = product.get3LastestProduct();
		List<Product> plist3_6lastest = product.get3_6LastestProduct();
		req.setAttribute("pList3Lastest", plist3lastest);
		req.setAttribute("pList3_6Lastest", plist3_6lastest);
		
        List<Product> listS = dao1.pagingSearch(txtSearch,index);
        int count = dao1.countSearch(txtSearch);
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
        
        req.setAttribute("txtSearch", txtSearch);
        req.setAttribute("endPage", endPage);
        req.setAttribute("tag", index);
        req.setAttribute("count", count);
        req.setAttribute("pList", listS);
        req.setAttribute("cList", listC);
        req.setAttribute("txtS", txtSearch);
      
        req.getRequestDispatcher("/views/web/search.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
