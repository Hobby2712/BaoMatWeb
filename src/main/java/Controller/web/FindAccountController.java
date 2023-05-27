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

import org.apache.commons.text.StringEscapeUtils;

import DAO.CategoryDAO;
import DAO.UserDAO;
import DaoImpl.CategoryDAOImpl;
import DaoImpl.UserDAOImpl;
import Entity.Category;
import Entity.User;
import Util.Constant;
@WebServlet(urlPatterns = { "/findAccount" })
public class FindAccountController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CategoryDAO category = new CategoryDAOImpl();
	private static String OTP;
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		String username_email = StringEscapeUtils.escapeHtml4(request.getParameter("username_email"));
		UserDAO dao = new UserDAOImpl();
        User u = dao.checkAccount(username_email);
        if(u != null){
            //Được đổi pass
        	String otp = dao.getRandom();
        	try {
				OTP = encryptOTP(otp);
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.print(otp);
			System.out.print(OTP);
        	//Category(Header)
    		List<Category> clist = category.getAllCategory1();
    		request.setAttribute("cList", clist);
    		List<Category> clist2 = category.getAllCategory2();
    		request.setAttribute("cList2", clist2);
        	request.setAttribute("user", username_email);
        	request.setAttribute("otpSend", OTP);
        	request.setAttribute("email", u.getEmail());
        	request.setAttribute("action", "verifyForgot");
        	request.setAttribute("cancel", "/Web/loginAccount");
        	dao.sendEmail(u.getEmail(), otp);
        	request.getRequestDispatcher("/views/web/otp.jsp").forward(request, response);
        }
        else{
            //day ve trang signup.jsp
        	request.setAttribute("mess", "Tài khoản không tồn tại");
        	request.getRequestDispatcher("/forgotAccountPass").forward(request, response);
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
