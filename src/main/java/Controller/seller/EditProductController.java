package Controller.seller;

import java.io.File;
import java.io.IOException;
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

@WebServlet(urlPatterns = { "/seller/edit" })
public class EditProductController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ProductDAO dao = new ProductDAOImpl();
	StoreDAO storeDao = new StoreDAOImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cookieHeader = String.format("JSESSIONID=%s; %s", req.getSession().getId(), Constant.sameSite);
		resp.setHeader("Set-Cookie", cookieHeader);
		resp.setHeader("X-Content-Type-Options", "nosniff");
		Product oldP = new Product();
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		servletFileUpload.setHeaderEncoding("UTF-8");
		try {
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			req.setCharacterEncoding("UTF-8");
			HttpSession session = req.getSession();
			User u = (User) session.getAttribute("acc");
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
				} else if (item.getFieldName().equals("id")) {
					oldP.setId(Integer.parseInt(StringEscapeUtils.escapeHtml4(item.getString("UTF-8"))));
				} else if (item.getFieldName().equals("name")) {
					oldP.setName(StringEscapeUtils.escapeHtml4(item.getString("UTF-8")));
				} else if (item.getFieldName().equals("price")) {
					oldP.setPrice(Integer.parseInt(StringEscapeUtils.escapeHtml4(item.getString("UTF-8"))));
				} else if (item.getFieldName().equals("image")) {
					if (item.getSize() > 0) {// neu co file d
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
						oldP.setImage("/uploads/product/" + fileName);
					} else {
						oldP.setImage(null);
					}
				} else if (item.getFieldName().equals("description")) {
					oldP.setDescription(StringEscapeUtils.escapeHtml4(item.getString("UTF-8")));
				} else if (item.getFieldName().equals("quantity")) {
					oldP.setQuantity(Integer.parseInt(StringEscapeUtils.escapeHtml4(item.getString("UTF-8"))));
				} else if (item.getFieldName().equals("category")) {
					oldP.setCateId(Integer.parseInt(StringEscapeUtils.escapeHtml4(item.getString("UTF-8"))));
				}
			}
			// TODO Auto-generated method stub
			Product newP = dao.getProductByID(Integer.toString(oldP.getId()));
			if (newP.getImage() != null) {
				// XOA ANH CU DI
				String fileName = newP.getImage();
				File file = new File(Constant.DIR + fileName);
				if (file.exists()) {
					file.delete();
				}
				newP.setImage(oldP.getImage());
			}
			oldP.setStoreId(storeDao.GetStoreIdFromUID(u.getId()));
			dao.editProduct(oldP);
			resp.sendRedirect("managerP");
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
