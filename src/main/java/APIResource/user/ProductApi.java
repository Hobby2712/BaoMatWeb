package APIResource.user;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
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
import com.microsoft.sqlserver.jdbc.SQLServerException;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import DaoImpl.CategoryDAOImpl;
import DaoImpl.ProductDAOImpl;
import Entity.Product;
import Entity.User;
import Entity.api.APIResponse;
import Util.Constant;

@WebServlet(urlPatterns = { "/api/v1/products", "/api/v1/products/*" })
public class ProductApi extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ProductDAO productDao;

	public ProductApi() {
		this.productDao = new ProductDAOImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.equals("/")) {
			// Xử lý yêu cầu lấy danh sách sản phẩm với các tham số tùy chọn
			String txtSearch = req.getParameter("search");
			String page = req.getParameter("page");
			String sort = req.getParameter("sort");
			String categoryId1 = req.getParameter("cid1");
			String categoryId2 = req.getParameter("cid2");
			String storeId = req.getParameter("store");

			List<Product> productList = null;

			if (txtSearch != null && !txtSearch.isEmpty()) {
				if (storeId != null && !storeId.isEmpty()) {
					productList = productDao.searchByNameAndStore(txtSearch, storeId);
				} else {
					// Tìm kiếm sản phẩm theo từ khóa
					productList = productDao.searchByName(txtSearch);
				}

			} else if (categoryId1 != null && !categoryId1.isEmpty()) {
				// Lấy danh sách sản phẩm theo danh mục
				productList = productDao.getProductByCID(categoryId1);
			} else if (categoryId2 != null && !categoryId2.isEmpty()) {
				// Lấy danh sách sản phẩm theo danh mục
				productList = productDao.getProductByCID2(categoryId2);
			} else {
				// Lấy danh sách tất cả sản phẩm
				productList = productDao.getAllProduct();
			}

			if (sort != null && !sort.isEmpty()) {
				// Sắp xếp sản phẩm theo tiêu chí
				// ...
			}

			if (page != null && !page.isEmpty()) {
				// Phân trang danh sách sản phẩm
				// ...
			}

			APIResponse<List<Product>> response = new APIResponse<>("success", false, "products", productList);
			sendJsonResponse(resp, response);
		} else {
			// Xử lý yêu cầu lấy thông tin sản phẩm có ID tương ứng
			if (pathInfo.split("/").length > 1) {
				if (pathInfo.equals("/latest")) {
					// Lấy sản phẩm mới nhất
					APIResponse<List<Product>> response = new APIResponse<>("success", false, "products",
							productDao.getLastestProduct());
					sendJsonResponse(resp, response);
				} else if (pathInfo.equals("/best-seller")) {
					// Lấy sản phẩm bán chạy nhất
					APIResponse<List<Product>> response = new APIResponse<>("success", false, "products",
							productDao.getBestSeller());
					sendJsonResponse(resp, response);
				} else {
					try {
						int id = Integer.parseInt(pathInfo.substring(1));
					} catch (NumberFormatException e) {
						resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						APIResponse<String> response = new APIResponse<>("Sai format", true);
						sendJsonResponse(resp, response);
						return;
					}
					Product product = productDao.getProductByID(pathInfo.split("/")[1]);
					if (product != null) {
						APIResponse<Product> response = new APIResponse<>("success", false, "product", product);
						sendJsonResponse(resp, response);
					} else {
						APIResponse<String> response = new APIResponse<>("Không tìm thấy sản phẩm", true);
						resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
						sendJsonResponse(resp, response);
					}
				}
			}
		}
	}

	/*
	 * Product p1 = new Product(1, "Product 1", 15000, "image1.jpg",
	 * "this is product 1 test", 40, 1, 2, 10); Product p2 = new Product(2,
	 * "Product 2", 180000, "image2.jpg", "this is product 2 test again", 40, 3, 2,
	 * 10); List<Product> plist = new ArrayList<>(); plist.add(p2); plist.add(p1);
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Product product = new Product();
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		servletFileUpload.setHeaderEncoding("UTF-8");
		try {
//			HttpSession session = req.getSession();
//			User u = (User) session.getAttribute("acc");
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			req.setCharacterEncoding("UTF-8");
			List<FileItem> items = servletFileUpload.parseRequest((HttpServletRequest) req);
			for (FileItem item : items) {
				if (item.getFieldName().equals("name")) {
					product.setName(item.getString("UTF-8"));
				} else if (item.getFieldName().equals("price")) {
					product.setPrice(Integer.parseInt(item.getString("UTF-8")));
				} else if (item.getFieldName().equals("image")) {

					String originalFileName = item.getName();
					int index = originalFileName.lastIndexOf(".");
					String ext = originalFileName.substring(index + 1);
					String fileName = System.currentTimeMillis() + "." + ext;
					File file = new File(Constant.DIR + "/uploads/product/" + fileName);
					item.write(file);
					product.setImage("/uploads/product/" + fileName);
				}
				if (item.getFieldName().equals("description")) {
					product.setDescription(item.getString("UTF-8"));
				}
				if (item.getFieldName().equals("quantity")) {
					product.setQuantity(Integer.parseInt(item.getString("UTF-8")));
				}
				if (item.getFieldName().equals("categoryId")) {
					product.setCateId(Integer.parseInt(item.getString("UTF-8")));
				}
				if (item.getFieldName().equals("storeId")) {
					product.setStoreId(Integer.parseInt(item.getString("UTF-8")));
				}

			}
			productDao.insertProduct(product);
			// Lấy sản phẩm vừa được insert vào database

			if (productDao.getLastestProduct(product.getName()) != null) {
				APIResponse<Product> response = new APIResponse<>("Thêm sản phẩm thành công", false, "product",
						productDao.getLastestProduct(product.getName()));
				sendJsonResponse(resp, response);
			} else {
				// Tạo đối tượng JSON chứa thông tin lỗi
				APIResponse<String> response = new APIResponse<>("Thêm sản phẩm thất bại", true);

				// Chuyển đổi đối tượng JSON thành chuỗi và trả về cho client
				sendJsonResponse(resp, response);
			}

		} catch (FileUploadException e) {
			e.printStackTrace();
			// Tạo đối tượng JSON chứa thông tin lỗi
			APIResponse<String> response = new APIResponse<>("Lỗi khi upload file", true);

			// Chuyển đổi đối tượng JSON thành chuỗi và trả về cho client
			sendJsonResponse(resp, response);
		} catch (Exception e) {
			e.printStackTrace();
			// Tạo đối tượng JSON chứa thông tin lỗi
			APIResponse<String> response = new APIResponse<>("Có lỗi trong quá trình thêm", true);

			// Chuyển đổi đối tượng JSON thành chuỗi và trả
			sendJsonResponse(resp, response);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		// Lấy id sản phẩm từ đường dẫn URL
		String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.equals("/")) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			APIResponse<String> response = new APIResponse<>("URL không đúng", true);
			sendJsonResponse(resp, response);
			return;
		}
		int id;
		try {
			id = Integer.parseInt(pathInfo.substring(1));
		} catch (NumberFormatException e) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			APIResponse<String> response = new APIResponse<>("Sai format", true);
			sendJsonResponse(resp, response);
			return;
		}
		Product existingProduct = productDao.getProductByID(Integer.toString(id));
		if (existingProduct == null) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			APIResponse<String> response = new APIResponse<>("Sản phẩm không tồn tại hoặc đã bị xóa", true);
			sendJsonResponse(resp, response);
			return;
		}

		// Lấy thông tin sản phẩm từ request body
		Product editProduct = new Product();
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		servletFileUpload.setHeaderEncoding("UTF-8");
		try {
//		        HttpSession session = req.getSession();
//		        User u = (User) session.getAttribute("acc");

			List<FileItem> items = servletFileUpload.parseRequest((HttpServletRequest) req);
			for (FileItem item : items) {
				if (item.getFieldName().equals("name")) {
					editProduct.setName(item.getString("UTF-8"));
				} else if (item.getFieldName().equals("price")) {
					editProduct.setPrice(Integer.parseInt(item.getString("UTF-8")));
				} else if (item.getFieldName().equals("image")) {
					String originalFileName = item.getName();
					int index = originalFileName.lastIndexOf(".");
					String ext = originalFileName.substring(index + 1);
					String fileName = System.currentTimeMillis() + "." + ext;
					File file = new File(Constant.DIR + "/uploads/product/" + fileName);
					item.write(file);
					editProduct.setImage("/uploads/product/" + fileName);
				}
				if (item.getFieldName().equals("description")) {
					editProduct.setDescription(item.getString("UTF-8"));
				}
				if (item.getFieldName().equals("quantity")) {
					editProduct.setQuantity(Integer.parseInt(item.getString("UTF-8")));
				}
				if (item.getFieldName().equals("categoryId")) {
					editProduct.setCateId(Integer.parseInt(item.getString("UTF-8")));
				}
				if (item.getFieldName().equals("storeId")) {
					editProduct.setStoreId(Integer.parseInt(item.getString("UTF-8")));
				}
			}
			if (editProduct.getImage() != null) {
				// XOA ANH CU DI
				String fileName = existingProduct.getImage();
				File file = new File(Constant.DIR + fileName);
				if (file.exists()) {
					file.delete();
				}
			} else {
				editProduct.setImage(existingProduct.getImage());
			}

			// Cập nhật thông tin sản phẩm vào database
			editProduct.setId(existingProduct.getId());

			// Trả về thông tin sản phẩm vừa cập nhật
			if (productDao.editApiProduct(editProduct)) {
				APIResponse<Product> response = new APIResponse<>("Cập nhật sản phẩm thành công", false, "product",
						productDao.getProductByID(Integer.toString(existingProduct.getId())));
				sendJsonResponse(resp, response);
			} else {
				APIResponse<String> response = new APIResponse<>("Cập nhật sản phẩm thất bại", true);
				// Chuyển đổi đối tượng JSON thành chuỗi và trả về cho client
				sendJsonResponse(resp, response);
			}

		} catch (FileUploadException e) {
			e.printStackTrace();
			// Tạo đối tượng JSON chứa thông tin lỗi
			APIResponse<String> response = new APIResponse<>("Lỗi khi upload file", true);

			// Chuyển đổi đối tượng JSON thành chuỗi và trả về cho client
			sendJsonResponse(resp, response);
		} catch (Exception e) {
			e.printStackTrace();
			// Tạo đối tượng JSON chứa thông tin lỗi
			APIResponse<String> response = new APIResponse<>("Có lỗi trong quá trình cập nhật", true);

			// Chuyển đổi đối tượng JSON thành chuỗi và trả
			sendJsonResponse(resp, response);
		}

	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.equals("/")) {
			// Nếu không có id sản phẩm được cung cấp trong đường dẫn, trả về lỗi 400 Bad
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			APIResponse<String> response = new APIResponse<>("URL không đúng", true);
			sendJsonResponse(resp, response);
			return;
		}
		// Lấy id sản phẩm từ đường dẫn
		int productId;
		try {
			productId = Integer.parseInt(pathInfo.substring(1));
		} catch (NumberFormatException e) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			APIResponse<String> response = new APIResponse<>("Sai format", true);
			sendJsonResponse(resp, response);
			return;
		}
		// Kiểm tra xem sản phẩm có tồn tại hay không
		Product product = productDao.getProductByID(Integer.toString(productId));
		if (product == null) {
			// Nếu sản phẩm không tồn tại, trả về lỗi 404 Not Found
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			APIResponse<String> response = new APIResponse<>("Sản phẩm không tồn tại hoặc đã bị xóa", true);
			sendJsonResponse(resp, response);
			return;
		}
		// Xóa sản phẩm khỏi cơ sở dữ liệu
		productDao.deleteProduct(productId);
		// Trả về thông báo xóa thành công
		APIResponse<String> response = new APIResponse<>("Xóa sản phẩm thành công", false);
		sendJsonResponse(resp, response);
	}

	private <T> void sendJsonResponse(HttpServletResponse resp, APIResponse<T> response) throws IOException {
		OutputStream outputStream = resp.getOutputStream();
		Gson gson = new Gson();
		outputStream.write(gson.toJson(response).getBytes());
		outputStream.flush();
	}
}
