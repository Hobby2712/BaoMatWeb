package Controller.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.CategoryDAO;
import DaoImpl.CategoryDAOImpl;
import Entity.Category;
import Util.Constant;

@WebServlet(urlPatterns = { "/forgotAccountPass" })
public class ForgotAccountPassController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CategoryDAO category = new CategoryDAOImpl();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cookieHeader = String.format("JSESSIONID=%s; %s", req.getSession().getId(), Constant.sameSite);
		resp.setHeader("Set-Cookie", cookieHeader);
		resp.setHeader("X-Content-Type-Options", "nosniff");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		// Category(Header)
		List<Category> clist = category.getAllCategory1();
		req.setAttribute("cList", clist);
		List<Category> clist2 = category.getAllCategory2();
		req.setAttribute("cList2", clist2);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/web/find-account.jsp");
		dispatcher.forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cookieHeader = String.format("JSESSIONID=%s; %s", req.getSession().getId(), Constant.sameSite);
		resp.setHeader("Set-Cookie", cookieHeader);
		resp.setHeader("X-Content-Type-Options", "nosniff");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		// Category(Header)
		List<Category> clist = category.getAllCategory1();
		req.setAttribute("cList", clist);
		List<Category> clist2 = category.getAllCategory2();
		req.setAttribute("cList2", clist2);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/web/find-account.jsp");
		dispatcher.forward(req, resp);
	}

}
