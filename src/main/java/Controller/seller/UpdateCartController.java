package Controller.seller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;

import DAO.CartDAO;
import DaoImpl.CartDAOImpl;
import Entity.Cart;
import Entity.User;
import Util.Constant;

@WebServlet(urlPatterns = { "/ajax/updateCart" })
public class UpdateCartController extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CartDAO dao = new CartDAOImpl();
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		String cookieHeader = String.format("JSESSIONID=%s; %s", req.getSession().getId(), Constant.sameSite);
		resp.setHeader("Set-Cookie", cookieHeader);
		resp.setHeader("X-Content-Type-Options", "nosniff");
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");
		int pid = Integer.parseInt(StringEscapeUtils.escapeHtml4(req.getParameter("pid")));
		int quantity = Integer.parseInt(StringEscapeUtils.escapeHtml4(req.getParameter("quantity")));
		try {
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			req.setCharacterEncoding("UTF-8");
			dao.updateCountItemCart(dao.getCartIdByUId(u.getId()), pid, quantity);
			Cart cart=dao.getItemByPID(dao.getCartIdByUId(u.getId()), pid);
			PrintWriter out = resp.getWriter();
			out.print(cart.getP().tienTe(cart.getTotalprice()));
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
