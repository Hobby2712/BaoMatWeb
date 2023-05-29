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
import Util.Constant;
import Util.PasswordEncoder;
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
		response.setHeader("X-Content-Type-Options", "nosniff");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		String email = StringEscapeUtils.escapeHtml4(request.getParameter("email"));
		String user = StringEscapeUtils.escapeHtml4(request.getParameter("user"));
		String passEncoder = StringEscapeUtils.escapeHtml4(request.getParameter("pass"));
		String otp = StringEscapeUtils.escapeHtml4(request.getParameter("otp"));
		String otp_send = StringEscapeUtils.escapeHtml4(request.getParameter("otpSend"));
		try {
			OTPSend = decrypt(otp_send);
			pass = PasswordEncoder.decrypt(passEncoder);
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

	public static String decrypt(String encryptedText) throws Exception {
		SecretKeySpec keySpec = new SecretKeySpec(Constant.SECRET_KEY.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, keySpec);
		byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
		return new String(decrypted, StandardCharsets.UTF_8);
	}
}