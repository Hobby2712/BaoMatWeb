package Controller.seller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
import org.apache.commons.text.StringEscapeUtils;
import org.apache.tika.Tika;
import org.apache.tika.mime.MediaType;

import DAO.ProductDAO;
import DAO.StoreDAO;
import DaoImpl.ProductDAOImpl;
import DaoImpl.StoreDAOImpl;
import Entity.Product;
import Entity.User;
import Util.Constant;
import Util.CsrfTokenUtil;

@WebServlet(urlPatterns = { "/seller/add" })
public class AddProductController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ProductDAO dao = new ProductDAOImpl();
	StoreDAO storeDao = new StoreDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setHeader("X-Content-Type-Options", "nosniff");
		String cookieHeader = String.format("JSESSIONID=%s; %s", req.getSession().getId(), Constant.sameSite);
		resp.setHeader("Set-Cookie", cookieHeader);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/seller/manager-product.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cookieHeader = String.format("JSESSIONID=%s; %s", req.getSession().getId(), Constant.sameSite);
		resp.setHeader("Set-Cookie", cookieHeader);
		resp.setHeader("X-Content-Type-Options", "nosniff");
		Product product = new Product();
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		servletFileUpload.setHeaderEncoding("UTF-8");
		try {
			HttpSession session = req.getSession();
			User u = (User) session.getAttribute("acc");
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
				} else if (item.getFieldName().equals("name")) {
					product.setName(StringEscapeUtils.escapeHtml4(item.getString("UTF-8")));
				} else if (item.getFieldName().equals("price")) {
					product.setPrice(Integer.parseInt(StringEscapeUtils.escapeHtml4(item.getString("UTF-8"))));
				} else if (item.getFieldName().equals("image")) {

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
					File file = new File(Constant.DIR + "/uploads/product/" + fileName);
					item.write(file);
					product.setImage("/uploads/product/" + fileName);
				}
				if (item.getFieldName().equals("description")) {
					product.setDescription(StringEscapeUtils.escapeHtml4(item.getString("UTF-8")));
				}
				if (item.getFieldName().equals("quantity")) {
					product.setQuantity(Integer.parseInt(StringEscapeUtils.escapeHtml4(item.getString("UTF-8"))));
				}
				if (item.getFieldName().equals("category")) {
					product.setCateId(Integer.parseInt(StringEscapeUtils.escapeHtml4(item.getString("UTF-8"))));
				}
				product.setStoreId(storeDao.GetStoreIdFromUID(u.getId()));
			}
			dao.insertProduct(product);
			resp.sendRedirect("managerP");
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
