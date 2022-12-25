package Controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import DAO.UserDAO;
import DaoImpl.UserDAOImpl;
import Entity.User;
import Util.Constant;

@WebServlet(urlPatterns = { "/admin/add" })
public class AddAccountController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	UserDAO dao = new UserDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/AccountManagement.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = new User();
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		servletFileUpload.setHeaderEncoding("UTF-8");
		try {
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			req.setCharacterEncoding("UTF-8");

			List<FileItem> items = servletFileUpload.parseRequest((HttpServletRequest) req);
			for (FileItem item : items) {
				if (item.getFieldName().equals("username")) {
					user.setUserName(item.getString("UTF-8"));
				}
				if (item.getFieldName().equals("fullname")) {
					user.setFullName((item.getString("UTF-8")));
				}
				if (item.getFieldName().equals("image")) {

					String originalFileName = item.getName();
					int index = originalFileName.lastIndexOf(".");
					String ext = originalFileName.substring(index + 1);
					String fileName = System.currentTimeMillis() + "." + ext;
					File file = new File(Constant.DIR + "/uploads/user/" + fileName);
					item.write(file);
					user.setImage("/uploads/user/" + fileName);
				}
				if (item.getFieldName().equals("email")) {
					user.setEmail(item.getString("UTF-8"));
				}
				if (item.getFieldName().equals("address")) {
					user.setAddress((item.getString("UTF-8")));
				}
				if (item.getFieldName().equals("password")) {
					user.setPass((item.getString("UTF-8")));
				}
				if (item.getFieldName().equals("phone")) {
					user.setPhone((item.getString("UTF-8")));
				}
				if (item.getFieldName().equals("role")) {
					user.setRole(Integer.parseInt(item.getString("UTF-8")));
				}

			}

			dao.addNewUser(user);
			resp.sendRedirect("ManagerAccount");
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
