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

@WebServlet(urlPatterns = { "/api/v1/shipper/orders" })
public class ShipperOrderApi extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private final OrderDAO orderDao;
	
	public ShipperOrderApi() {
		this.orderDao = new OrderDAOImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		
		String status = req.getParameter("status");
		if(status == null){
			APIResponse<List<OrderDetails>> response = new APIResponse<>("success", false, "orders",
					orderDao.getAllOrderShipper());
			sendJsonResponse(resp, response);
		}
		else {
			try {
				int statusId = Integer.parseInt(status);
				APIResponse<List<OrderDetails>> response = new APIResponse<>("success", false, "orders",
						orderDao.getOrderByStatus(statusId));
				sendJsonResponse(resp, response);
			} catch (Exception e) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		        APIResponse<String> response = new APIResponse<>("Sai format", true);
		        sendJsonResponse(resp, response);
			}
		}
	}
	
	private <T> void sendJsonResponse(HttpServletResponse resp, APIResponse<T> response) throws IOException {
		OutputStream outputStream = resp.getOutputStream();
		Gson gson = new Gson();
		outputStream.write(gson.toJson(response).getBytes());
		outputStream.flush();
	}
}
