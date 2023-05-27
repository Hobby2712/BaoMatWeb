package APIResource.user;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import DAO.UserDAO;
import DaoImpl.CategoryDAOImpl;
import DaoImpl.ProductDAOImpl;
import DaoImpl.UserDAOImpl;
import Entity.Product;
import Entity.User;
import Entity.api.APIResponse;
import Util.Constant;

@WebServlet(urlPatterns = {"/api/v1/profile/*"})
public class ProfileApi extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        
        UserDAO dao = new UserDAOImpl();
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");
		try {
		if(u!= null) {
		APIResponse<User> response = new APIResponse<>("Get user thành công",false, "user", u);
        OutputStream outputStream = resp.getOutputStream();
        Gson gson = new Gson();
        outputStream.write(gson.toJson(response).getBytes());
        outputStream.flush();}
		else {
			APIResponse<User> response = new APIResponse<>("Get user thất bại",true);
			OutputStream outputStream = resp.getOutputStream();
	        Gson gson = new Gson();
	        outputStream.write(gson.toJson(response).getBytes());
	        outputStream.flush();
		}
		}
		catch(Exception e){
			e.printStackTrace();
			APIResponse<User> response = new APIResponse<>("Lỗi request",true);
	        OutputStream outputStream = resp.getOutputStream();
	        Gson gson = new Gson();
	        outputStream.write(gson.toJson(response).getBytes());
	        outputStream.flush();
		}
        
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        
        UserDAO dao = new UserDAOImpl();
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");
		
		String name = req.getParameter("name");
		String adress = req.getParameter("address");
		String phone = req.getParameter("phone");
		
		try {
		dao.updateProfile(name, adress, phone, u.getId());
		session.setAttribute("acc", dao.login(u.getUserName(), u.getPass()));
		
		
		APIResponse<User> response = new APIResponse<>("Edit user thành công",false,"user",u);
        OutputStream outputStream = resp.getOutputStream();
        Gson gson = new Gson();
        outputStream.write(gson.toJson(response).getBytes());
        outputStream.flush();
        }
		catch(Exception e){
			e.printStackTrace();
			APIResponse<User> response = new APIResponse<>("Edit user thất bại",true);
	        OutputStream outputStream = resp.getOutputStream();
	        Gson gson = new Gson();
	        outputStream.write(gson.toJson(response).getBytes());
	        outputStream.flush();
		}
        
	}
}