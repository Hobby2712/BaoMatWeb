package APIResource.user;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import DAO.UserDAO;
import DaoImpl.UserDAOImpl;
import Entity.User;
import Entity.api.APIResponse;

@WebServlet(urlPatterns = {"/api/v1/signup/*"})
public class SignupApi extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        //String pathInfo = req.getPathInfo();
        String email = req.getParameter("email");
        String user = req.getParameter("user");
		String pass = req.getParameter("pass");
		String repass = req.getParameter("repass");
		
		try{
			if (!pass.equals(repass)) {
				APIResponse<String> response = new APIResponse<>("Đăng kí thất bại, mật khẩu không trùng khớp", true);
				OutputStream outputStream = resp.getOutputStream();
			    Gson gson = new Gson();
			    outputStream.write(gson.toJson(response).getBytes());
			    outputStream.flush();
			} else {
				UserDAO dao = new UserDAOImpl();
				User u = dao.checkAccountExist(user);
				if (dao.checkEmailExist(email) != null) {
					//req.setAttribute("mess", "Email đã được sử dụng");
					//req.getRequestDispatcher("/signUpAccount").forward(req, resp);
					APIResponse<String> response = new APIResponse<>("Email đã được đăng kí", true);
					OutputStream outputStream = resp.getOutputStream();
				    Gson gson = new Gson();
				    outputStream.write(gson.toJson(response).getBytes());
				    outputStream.flush();
				} else if (u == null) {
					// dc signup
					
					String otp = dao.getRandom();
					System.out.print(otp);
					req.setAttribute("user", user);
					req.setAttribute("pass", pass);
					req.setAttribute("email", email);
					req.setAttribute("otpSend", otp);
					dao.sendEmail(email, otp);
					//req.setAttribute("action", "verify");
					//req.setAttribute("cancel", "/Web/loginAccount");
					
					
					APIResponse<String> response = new APIResponse<>("Đã gửi OTP thành công", false, "otp", otp);
					OutputStream outputStream = resp.getOutputStream();
				    Gson gson = new Gson();
				    outputStream.write(gson.toJson(response).getBytes());
				    outputStream.flush();
					//req.getRequestDispatcher("/views/web/otp.jsp").forward(req, resp);
				}
				else {
					resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Account already exists");
				}
		}

	}catch (Exception e) {
        e.printStackTrace();
        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Đã xảy ra lỗi khi xử lý request");
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
}
	