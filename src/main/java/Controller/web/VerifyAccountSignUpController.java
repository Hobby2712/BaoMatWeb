package Controller.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;

import DAO.CategoryDAO;
import DAO.UserDAO;
import DaoImpl.CategoryDAOImpl;
import DaoImpl.UserDAOImpl;
import Entity.Category;
import Entity.User;
import Util.AES;
import Util.Constant;
import Util.CsrfTokenUtil;
import Util.KeyGenerator2;

@WebServlet(urlPatterns = { "/verify" })
public class VerifyAccountSignUpController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CategoryDAO category = new CategoryDAOImpl();
	private static String OTPSend;
	private static String pass;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cookieHeader = String.format("JSESSIONID=%s; %s", request.getSession().getId(), Constant.sameSite);
		response.setHeader("Set-Cookie", cookieHeader);
		response.setHeader("X-Content-Type-Options", "nosniff");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String csrfToken = request.getParameter("csrf_token");
	    System.out.println(csrfToken);
		if (csrfToken == null || !csrfToken.equals(request.getSession().getAttribute("csrf_token"))) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Invalid CSRF token");
			System.out.println(request.getSession().getAttribute("csrf_token"));
		    return;
		}
		//Tạo token mới lên session
        csrfToken = CsrfTokenUtil.generateCsrfToken();
        request.getSession().setAttribute("csrf_token", csrfToken);

		String email = StringEscapeUtils.escapeHtml4(request.getParameter("email"));
		String user = StringEscapeUtils.escapeHtml4(request.getParameter("user"));
		String passEncoder = StringEscapeUtils.escapeHtml4(request.getParameter("pass"));
		String otp = StringEscapeUtils.escapeHtml4(request.getParameter("otp"));
		String otp_send = StringEscapeUtils.escapeHtml4(request.getParameter("otpSend"));
		try {
			OTPSend = AES.decrypt(otp_send, KeyGenerator2.getSecretKey());
			pass = AES.decrypt(passEncoder, KeyGenerator2.getSecretKey());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Category(Header)
		List<Category> clist = category.getAllCategory1();
		request.setAttribute("cList", clist);
		List<Category> clist2 = category.getAllCategory2();
		request.setAttribute("cList2", clist2);

		if (!otp.equals(OTPSend)) {
			request.setAttribute("mess", "Mã OTP sai!");
			request.setAttribute("user", user);
			request.setAttribute("pass", pass);
			request.setAttribute("email", email);
			request.setAttribute("otpSend", otp_send);
			request.setAttribute("action", "verify");
			request.setAttribute("cancel", "/Web/loginAccount");
			request.getRequestDispatcher("/views/web/otp.jsp").forward(request, response);
		} else {
			UserDAO dao = new UserDAOImpl();
			dao.singup(user, pass, email);
			User u = dao.login(user, pass);
			HttpSession session = request.getSession();
			session.setAttribute("acc", u);
			session.setMaxInactiveInterval(1000);
			response.sendRedirect("home");
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