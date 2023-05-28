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

import DAO.ProductDAO;
import DAO.StoreDAO;
import DaoImpl.ProductDAOImpl;
import DaoImpl.StoreDAOImpl;
import Entity.Product;
import Entity.User;
import Util.Constant;

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
				if (item.getFieldName().equals("id")) {
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
