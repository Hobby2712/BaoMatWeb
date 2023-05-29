package Controller.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.CartDAO;
import DaoImpl.CartDAOImpl;
import Entity.Product;
import Entity.User;
import Util.Constant;

@WebServlet(urlPatterns = { "/ajax/totalCart" })
public class GetTotalController extends HttpServlet{
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
		
		try {
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			req.setCharacterEncoding("UTF-8");
			Product total = new Product();
			PrintWriter out = resp.getWriter();
			out.print(total.tienTe(dao.getTotalCart(dao.getCartIdByUId(u.getId()))));
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
