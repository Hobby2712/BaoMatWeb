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
import DAO.OrderDAO;
import DaoImpl.CartDAOImpl;
import DaoImpl.OrderDAOImpl;
import Entity.Cart;
import Entity.User;
import Util.Constant;
import Util.CsrfTokenUtil;

@WebServlet(urlPatterns = { "/addOrder" })
public class AddOrderController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	OrderDAO dao = new OrderDAOImpl();
	CartDAO cdao = new CartDAOImpl();
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		String cookieHeader = String.format("JSESSIONID=%s; %s", req.getSession().getId(), Constant.sameSite);
		resp.setHeader("Set-Cookie", cookieHeader);
		resp.setHeader("X-Content-Type-Options", "nosniff");
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");
		String csrfToken = req.getParameter("csrf_token");
	    System.out.println(csrfToken);
		if (csrfToken == null || !csrfToken.equals(req.getSession().getAttribute("csrf_token"))) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.getWriter().write("Invalid CSRF token");
			System.out.println(req.getSession().getAttribute("csrf_token"));
		    return;
		}
		//Tạo token mới lên session
        csrfToken = CsrfTokenUtil.generateCsrfToken();
        req.getSession().setAttribute("csrf_token", csrfToken);
		String name = StringEscapeUtils.escapeHtml4(req.getParameter("name"));
		String phone = StringEscapeUtils.escapeHtml4(req.getParameter("phone"));
		String address = StringEscapeUtils.escapeHtml4(req.getParameter("address"));
		try {
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			req.setCharacterEncoding("UTF-8");
			dao.insertOrder(u.getId(), name, phone, address);
			List <Cart> a = cdao.getAllItemsCart(cdao.getCartIdByUId(u.getId()), 1);
			for(Cart aitems : a) {
				dao.insertOrderItems(dao.getOrderIdByUId(u.getId()), aitems.getP().getId(), aitems.getCount());
			}
			cdao.deleteAllItemCart(cdao.getCartIdByUId(u.getId()));
			resp.sendRedirect("order");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
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
