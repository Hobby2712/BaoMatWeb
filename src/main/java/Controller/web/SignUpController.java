package Controller.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;

import DAO.CategoryDAO;
import DAO.UserDAO;
import DaoImpl.CategoryDAOImpl;
import DaoImpl.UserDAOImpl;
import Entity.Category;
import Entity.User;

@WebServlet(urlPatterns = { "/signup" })
public class SignUpController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CategoryDAO category = new CategoryDAOImpl();

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		String email = StringEscapeUtils.escapeHtml4(request.getParameter("email"));
		String user = StringEscapeUtils.escapeHtml4(request.getParameter("user"));
		String pass = StringEscapeUtils.escapeHtml4(request.getParameter("pass"));
		String repass = StringEscapeUtils.escapeHtml4(request.getParameter("repass"));

		// Category(Header)
		List<Category> clist = category.getAllCategory1();
		request.setAttribute("cList", clist);
		List<Category> clist2 = category.getAllCategory2();
		request.setAttribute("cList2", clist2);

		if (!pass.equals(repass)) {
			request.setAttribute("mess", "Mật khẩu không khớp");
			request.getRequestDispatcher("/signUpAccount").forward(request, response);
		} else {
			UserDAO dao = new UserDAOImpl();
			User u = dao.checkAccountExist(user);
			if (dao.checkEmailExist(email) != null) {
				// day ve trang signup.jsp
				request.setAttribute("mess", "Email đã được sử dụng");
				request.getRequestDispatcher("/signUpAccount").forward(request, response);
			} else if (u == null) {
				// dc signup
				String otp = dao.getRandom();
				request.setAttribute("user", user);
				request.setAttribute("pass", pass);
				request.setAttribute("email", email);
				request.setAttribute("otpSend", otp);
				request.setAttribute("action", "verify");
				request.setAttribute("cancel", "/Web/loginAccount");
				dao.sendEmail(email, otp);
				request.getRequestDispatcher("/views/web/otp.jsp").forward(request, response);
			}

			else {
				// day ve trang signup.jsp
				request.setAttribute("mess", "Tài khoản đã tồn tại");
				request.getRequestDispatcher("/signUpAccount").forward(request, response);
			}
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
