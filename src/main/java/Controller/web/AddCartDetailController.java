package Controller.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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

@WebServlet(urlPatterns = { "/addCartDetail" })
public class AddCartDetailController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CartDAO dao = new CartDAOImpl();

	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");
		if (u != null) {
			int pid = Integer.parseInt(StringEscapeUtils.escapeHtml4(req.getParameter("pid")));
			int quantity = Integer.parseInt(StringEscapeUtils.escapeHtml4(req.getParameter("quantity")));
			try {
				resp.setContentType("text/html");
				resp.setCharacterEncoding("UTF-8");
				req.setCharacterEncoding("UTF-8");
				Cart check = dao.getItemByPID(dao.getCartIdByUId(u.getId()), pid);
				if (check == null) {
					dao.insertCartItemDetail(dao.getCartIdByUId(u.getId()), pid, quantity);
				} else {
					dao.updateCountItemDetail(dao.getCartIdByUId(u.getId()), pid, quantity);
				}
				resp.sendRedirect("detail" + "?pid=" + Integer.toString(pid));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			req.setAttribute("mess", "Đăng nhập để mua hàng");
			RequestDispatcher dispatcher = req.getRequestDispatcher("/loginAccount");
			dispatcher.forward(req, resp);
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
