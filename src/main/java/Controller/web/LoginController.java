package Controller.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;

import DAO.UserDAO;
import DaoImpl.UserDAOImpl;
import Entity.User;
import Util.CsrfTokenUtil;

@WebServlet(urlPatterns = { "/login" })
public class LoginController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String sameSite = "SameSite=Strict";
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cookieHeader = String.format("JSESSIONID=%s; %s", request.getSession().getId(), sameSite);
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
			//System.out.println(request.getSession().getAttribute("csrf_token"));
		    return;
		}
		
		
		//Tạo token mới lên session
        csrfToken = CsrfTokenUtil.generateCsrfToken();
        request.getSession().setAttribute("csrf_token", csrfToken);

		String username = StringEscapeUtils.escapeHtml4(request.getParameter("user"));
		String password = StringEscapeUtils.escapeHtml4(request.getParameter("pass"));
		//String passwordEncrypt = PasswordEncoder.encrypt(password);
		Boolean remember = Boolean.parseBoolean(request.getParameter("remember"));
		UserDAO dao = new UserDAOImpl();
		User u = dao.login(username, password);
		if (u == null) {
			request.setAttribute("mess", "Sai tài khoản hoặc mật khẩu!!");
			request.getRequestDispatcher("/loginAccount").forward(request, response);
		} else {
			HttpSession session = request.getSession();
			
			
			session.setAttribute("acc", u);
			session.setMaxInactiveInterval(1000);
			if (remember == true) {
				String sanitizedUsername = username.replaceAll("[\r\n]", "");
				Cookie uNameCookie = new Cookie("username", sanitizedUsername);
				String uNamecookieHeader = String.format("%s=%s; %s", uNameCookie, username, sameSite); 
				response.setHeader("Set-Cookie", uNamecookieHeader);
				
				uNameCookie.setMaxAge(24 * 3600);
				uNameCookie.setHttpOnly(true);
				uNameCookie.setSecure(true);
				String sanitizedPassword = username.replaceAll("[\r\n]", "");
				Cookie passCookie = new Cookie("pass", sanitizedPassword);
				
				String uPasscookieHeader = String.format("%s=%s; %s", passCookie, password, sameSite); 
				response.setHeader("Set-Cookie", uPasscookieHeader);
				
				passCookie.setMaxAge(24 * 3600);
				passCookie.setHttpOnly(true);
				passCookie.setSecure(true);
				//uNameCookie.setSameSite("Strict");
				response.addCookie(uNameCookie);
				response.addCookie(passCookie);
			}
			if (u.getRole() == 2) {
				response.sendRedirect("home");
			} else if (u.getRole() == 1) {
				response.sendRedirect("admin/homeAdmin");
			} else if (u.getRole() == 3) {
				response.sendRedirect("shipper/homeShipper");
			} else {
				response.sendRedirect("seller/homeSeller");
			}
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
