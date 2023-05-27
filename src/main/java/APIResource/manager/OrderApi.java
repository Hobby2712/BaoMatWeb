package APIResource.manager;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;

import DAO.OrderDAO;

import DaoImpl.OrderDAOImpl;
import Entity.OrderDetails;
import Entity.api.APIResponse;

@WebServlet(urlPatterns = { "/api/v1/orders", "/api/v1/orders/*" })
public class OrderApi extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final OrderDAO orderDao;

	public OrderApi() {
		this.orderDao = new OrderDAOImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.equals("/")) {
			// Handle GET request for all orders for a store
			String storeId = req.getParameter("store");
			String userId = req.getParameter("user");
			String id = req.getParameter("id");
			String status = req.getParameter("status");
			if (userId != null && !userId.isEmpty()) {
				APIResponse<List<OrderDetails>> response = new APIResponse<>("success", false, "orders",
						orderDao.getAllItemsOrder(Integer.parseInt(userId)));
				sendJsonResponse(resp, response);
			} else if (storeId != null && !storeId.isEmpty()) {
				APIResponse<List<OrderDetails>> response = new APIResponse<>("success", false, "orders",
						orderDao.getAllOrder(Integer.parseInt(storeId)));
				sendJsonResponse(resp, response);
			} else if (id != null && !id.isEmpty() && status != null && !status.isEmpty()) {
				try {
					int orderId = Integer.parseInt(id);
					int statusId = Integer.parseInt(status);
					if(orderDao.updateOrderStatusApi(orderId, statusId)) {
						APIResponse<String> response = new APIResponse<>("success", false);
						sendJsonResponse(resp, response);
					} else {
						APIResponse<String> response = new APIResponse<>("fail", false);
						sendJsonResponse(resp, response);
					}
				} catch (Exception e) {
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			        APIResponse<String> response = new APIResponse<>("Sai format", true);
			        sendJsonResponse(resp, response);
				}
			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		        APIResponse<String> response = new APIResponse<>("URL không đúng", true);
		        sendJsonResponse(resp, response);
			}
		} else {
			String[] pathParts = pathInfo.split("/");
			if (pathParts.length > 1) {
				if (pathInfo.equals("/count-status")) {
					String storeId = req.getParameter("store");
					if (storeId != null && !storeId.isEmpty()) {
						APIResponse<List<OrderDetails>> response = new APIResponse<>("success", false, "orders",
								orderDao.getCountOrderStatus(Integer.parseInt(storeId)));
						sendJsonResponse(resp, response);
					}else {
						resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				        APIResponse<String> response = new APIResponse<>("URL thiếu param", true);
				        sendJsonResponse(resp, response);
					}
				} else if (pathInfo.equals("/count-all")) {
					String storeId = req.getParameter("store");
					if (storeId != null && !storeId.isEmpty()) {
						int count_all = orderDao.getCountOrderStatus(Integer.parseInt(storeId))
								.stream()
								.mapToInt(OrderDetails::getCount)
								.sum();

						APIResponse<Integer> response = new APIResponse<>("success", false, "total_orders", count_all);
						sendJsonResponse(resp, response);
					} else {
						resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				        APIResponse<String> response = new APIResponse<>("URL thiếu param", true);
				        sendJsonResponse(resp, response);
					}
				} else {
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			        APIResponse<String> response = new APIResponse<>("URL không đúng", true);
			        sendJsonResponse(resp, response);
				}
			}

		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPut(req, resp);
	}

	private <T> void sendJsonResponse(HttpServletResponse resp, APIResponse<T> response) throws IOException {
		OutputStream outputStream = resp.getOutputStream();
		Gson gson = new Gson();
		outputStream.write(gson.toJson(response).getBytes());
		outputStream.flush();
	}
}
