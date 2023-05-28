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
import org.apache.commons.text.StringEscapeUtils;
import org.apache.tika.Tika;
import org.apache.tika.mime.MediaType;

import DAO.UserDAO;
import DaoImpl.UserDAOImpl;
import Entity.User;
import Util.Constant;
import Util.CsrfTokenUtil;

@WebServlet(urlPatterns = { "/admin/add" })
public class AddAccountController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	UserDAO dao = new UserDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 resp.setHeader("X-Content-Type-Options", "nosniff");
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/AccountManagement.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("X-Content-Type-Options", "nosniff");
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
				if (item.getFieldName().equals("csrf_token")) {
					System.out.println(item.getString());
					if (item.getString() == null || !item.getString().equals(req.getSession().getAttribute("csrf_token"))) {
						resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						resp.getWriter().write("Invalid CSRF token");
						//System.out.println(request.getSession().getAttribute("csrf_token"));
					    return;
					}
					//Tạo token mới lên session
			        String csrfToken = CsrfTokenUtil.generateCsrfToken();
			        req.getSession().setAttribute("csrf_token", csrfToken);
				} else if (item.getFieldName().equals("username")) {
					user.setUserName(StringEscapeUtils.escapeHtml4(item.getString("UTF-8")));
				}
				if (item.getFieldName().equals("fullname")) {
					user.setFullName((StringEscapeUtils.escapeHtml4(item.getString("UTF-8"))));
				}
				if (item.getFieldName().equals("image")) {

					String originalFileName = StringEscapeUtils.escapeHtml4(item.getName());
					int index = originalFileName.lastIndexOf(".");
					String ext = originalFileName.substring(index + 1);
					Tika tika = new Tika();
				    MediaType mediaType = MediaType.parse(tika.detect(item.getInputStream()));
				    if (!mediaType.getType().equals("image") || 
				        (!mediaType.getSubtype().equals("jpeg") && !mediaType.getSubtype().equals("jpg") && !mediaType.getSubtype().equals("png"))) {
				        throw new Exception("Only JPEG, JPG, and PNG image files are allowed");
				    }
					String fileName = System.currentTimeMillis() + "." + ext;
					File file = new File(Constant.DIR + "/uploads/user/" + fileName);
					item.write(file);
					user.setImage("/uploads/user/" + fileName);
				}
				if (item.getFieldName().equals("email")) {
					user.setEmail(StringEscapeUtils.escapeHtml4(item.getString("UTF-8")));
				}
				if (item.getFieldName().equals("address")) {
					user.setAddress((StringEscapeUtils.escapeHtml4(item.getString("UTF-8"))));
				}
				if (item.getFieldName().equals("password")) {
					user.setPass((StringEscapeUtils.escapeHtml4(item.getString("UTF-8"))));
				}
				if (item.getFieldName().equals("phone")) {
					user.setPhone((StringEscapeUtils.escapeHtml4(item.getString("UTF-8"))));
				}
				if (item.getFieldName().equals("role")) {
					user.setRole(Integer.parseInt(StringEscapeUtils.escapeHtml4(item.getString("UTF-8"))));
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
