
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

@WebServlet(urlPatterns = {"/detail"})
public class DetailController extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	CategoryDAO category = new CategoryDAOImpl();
	CartDAO cart = new CartDAOImpl();
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		String cookieHeader = String.format("JSESSIONID=%s; %s", req.getSession().getId(), Constant.sameSite);
		resp.setHeader("Set-Cookie", cookieHeader);
		resp.setHeader("X-Content-Type-Options", "nosniff");
    	resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		
        String id = StringEscapeUtils.escapeHtml4(req.getParameter("pid"));
        ProductDAO dao = new ProductDAOImpl();
        Product p = dao.getProductByID(id);
        
        //Category(Header)
      	List<Category> clist = category.getAllCategory1();
      	req.setAttribute("cList", clist);
      	List<Category> clist2 = category.getAllCategory2();
      	req.setAttribute("cList2", clist2);

        req.setAttribute("detail", p);
        //req.setAttribute("listCC", listC);
        
        HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");

		if (u != null) {
			int countCart = cart.countCart(cart.getCartIdByUId(u.getId()));
			req.setAttribute("countC", countCart);
		}
        req.getRequestDispatcher("/views/web/shop-details.jsp").forward(req, resp);
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


