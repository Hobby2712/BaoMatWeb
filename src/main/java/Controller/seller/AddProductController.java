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

import DAO.ProductDAO;
import DAO.StoreDAO;
import DaoImpl.ProductDAOImpl;
import DaoImpl.StoreDAOImpl;
import Entity.Product;
import Entity.User;
import Util.Constant;

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
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/seller/manager-product.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
				if (item.getFieldName().equals("name")) {
					product.setName(StringEscapeUtils.escapeHtml4(item.getString("UTF-8")));
				} else if (item.getFieldName().equals("price")) {
					product.setPrice(Integer.parseInt(StringEscapeUtils.escapeHtml4(item.getString("UTF-8"))));
				} else if (item.getFieldName().equals("image")) {

					String originalFileName = StringEscapeUtils.escapeHtml4(item.getName());
					int index = originalFileName.lastIndexOf(".");
					String ext = originalFileName.substring(index + 1);
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
