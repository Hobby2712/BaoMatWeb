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
import Entity.Product;
import Entity.User;
import Entity.api.APIResponse;

@WebServlet(urlPatterns = {"/api/v1/login/*"})
public class LoginApi extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        //String pathInfo = req.getPathInfo();
        String username = req.getParameter("user");
		String password = req.getParameter("pass");
		Boolean remember = Boolean.parseBoolean(req.getParameter("remember"));
		UserDAO dao = new UserDAOImpl();
		User u = dao.login(username, password);
		try{
			if (u == null) {
			APIResponse<String> response = new APIResponse<>("Đăng nhập thất bại", true);
			// Chuyển đổi đối tượng JSON thành chuỗi và trả về cho client
			OutputStream outputStream = resp.getOutputStream();
		    Gson gson = new Gson();
		    outputStream.write(gson.toJson(response).getBytes());
		    outputStream.flush();
		    
		} else {
			HttpSession session = req.getSession();
			session.setAttribute("acc", u);
			session.setMaxInactiveInterval(1000);
			APIResponse<User> response = new APIResponse<>("Đăng nhập thành công", false, "user", u);
			OutputStream outputStream = resp.getOutputStream();
	        Gson gson = new Gson();
	        outputStream.write(gson.toJson(response).getBytes());
	        outputStream.flush();
			if (remember == true) {
				Cookie uNameCookie = new Cookie("username", username);
				uNameCookie.setMaxAge(24 * 3600);
				Cookie passCookie = new Cookie("pass", password);
				passCookie.setMaxAge(24 * 3600);
				resp.addCookie(uNameCookie);
				resp.addCookie(passCookie);
			}
			
		}
	}catch (Exception e) {
        e.printStackTrace();
        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred while processing request");
    }
    }

}
	