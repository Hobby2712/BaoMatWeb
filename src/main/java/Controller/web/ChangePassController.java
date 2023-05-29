package Controller.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;

import DAO.CartDAO;
import DAO.CategoryDAO;
import DAO.UserDAO;
import DaoImpl.CartDAOImpl;
import DaoImpl.CategoryDAOImpl;
import DaoImpl.UserDAOImpl;
import Entity.Category;
import Entity.User;
import Util.Constant;
import Util.PasswordEncoder;
@WebServlet(urlPatterns = { "/changePassword" })
public class ChangePassController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CategoryDAO category = new CategoryDAOImpl();
	CartDAO cart = new CartDAOImpl();
	private static String OTP;
	private static String passwordEncoder;
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("X-Content-Type-Options", "nosniff");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("acc");

		String oldpass = StringEscapeUtils.escapeHtml4(request.getParameter("oldPass"));
		String pass = StringEscapeUtils.escapeHtml4(request.getParameter("newPass"));
		String repass = StringEscapeUtils.escapeHtml4(request.getParameter("repeatNewPass"));

		// Category(Header)
		List<Category> clist = category.getAllCategory1();
		request.setAttribute("cList", clist);
		List<Category> clist2 = category.getAllCategory2();
		request.setAttribute("cList2", clist2);
		
		int countCart = cart.countCart(cart.getCartIdByUId(u.getId()));
		request.setAttribute("countC", countCart);

		if (!pass.equals(repass)) {
			request.setAttribute("mess", "Mật khẩu không khớp");
			request.getRequestDispatcher("/profile").forward(request, response);
		} else if (!oldpass.equals(u.getPass())) {
			request.setAttribute("mess", "Mật khẩu sai");
			request.getRequestDispatcher("/profile").forward(request, response);
		} else {
			UserDAO dao = new UserDAOImpl();
			// dc signup
			String otp = dao.getRandom();
			
        	try {
				OTP = encryptOTP(otp);
				passwordEncoder = PasswordEncoder.encrypt(pass);
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.print(otp);
			System.out.print(OTP);
			request.setAttribute("user", u.getUserName());
			request.setAttribute("pass", passwordEncoder);
			request.setAttribute("email", u.getEmail());
			request.setAttribute("otpSend", OTP);
			request.setAttribute("action", "verifyChangePass");
			request.setAttribute("cancel", "/Web/profile");
			dao.sendEmail(u.getEmail(), otp);
			request.getRequestDispatcher("/views/web/otp.jsp").forward(request, response);
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
public static String encryptOTP(String OTP) throws Exception{
		
		SecretKeySpec keySpec = new SecretKeySpec(Constant.SECRET_KEY.getBytes(), "AES");
	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	    cipher.init(Cipher.ENCRYPT_MODE, keySpec);
	    byte[] encrypted = cipher.doFinal(OTP.getBytes(StandardCharsets.UTF_8));
	    return Base64.getEncoder().encodeToString(encrypted);
	}
}
