package Controller.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.text.StringEscapeUtils;

import DAO.UserDAO;
import DaoImpl.UserDAOImpl;
import Entity.User;

@WebServlet(urlPatterns = { "/editProfile" })
public class EditProfileController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		resp.setHeader("X-Content-Type-Options", "nosniff");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		
		UserDAO dao = new UserDAOImpl();
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");
		String name = StringEscapeUtils.escapeHtml4(req.getParameter("name"));
		String adress = StringEscapeUtils.escapeHtml4(req.getParameter("address"));
		String phone = StringEscapeUtils.escapeHtml4(req.getParameter("phone"));
		
		dao.updateProfile(name, adress, phone, u.getId());
		session.setAttribute("acc", dao.login(u.getUserName(), u.getPass()));
		resp.sendRedirect("/Web/views/web/profile.jsp");
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
