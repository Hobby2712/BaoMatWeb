package APIResource.manager;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import DAO.ThongKeDAO;
import DaoImpl.ThongKeDAOImpl;
import Entity.Chart;
import Entity.ThongKe;
import Entity.api.APIResponse;

@WebServlet(urlPatterns = {"/api/v1/statistic", "/api/v1/statistic/*"})
public class StatisticApi extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private final ThongKeDAO thongKeDao;
	public StatisticApi() {
		this.thongKeDao = new ThongKeDAOImpl();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		
		String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.equals("/")) {
			String index = req.getParameter("index");
			String store = req.getParameter("store");
			String year = req.getParameter("year");
			if(store == null || store.isEmpty()) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				APIResponse<String> response = new APIResponse<>("Không có param", true);
				sendJsonResponse(resp, response);
				return;
			}
			
			int storeId;
			
			try {
				storeId = Integer.parseInt(store);
			} catch (NumberFormatException e) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				APIResponse<String> response = new APIResponse<>("Sai format", true);
				sendJsonResponse(resp, response);
				return;
			}
			
			if(year == null){
				year = thongKeDao.getLastedYearByStore(storeId);
			}
			
			if(index == null){
				index = "1";
			}
			APIResponse<List<ThongKe>> response = new APIResponse<>("success", false, "statistic",
					thongKeDao.getAllThongKeByStore(Integer.parseInt(year), storeId, Integer.parseInt(index)));
			sendJsonResponse(resp, response);
			
		} else {
			String[] pathParts = pathInfo.split("/");
			if (pathParts.length > 1) {
				if (pathInfo.equals("/page")) {
					String index = req.getParameter("index");
					String store = req.getParameter("store");
					String year = req.getParameter("year");
					if(store == null || store.isEmpty()) {
						resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						APIResponse<String> response = new APIResponse<>("Không có param", true);
						sendJsonResponse(resp, response);
						return;
					}
					
					int storeId;
					
					try {
						storeId = Integer.parseInt(store);
					} catch (NumberFormatException e) {
						resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						APIResponse<String> response = new APIResponse<>("Sai format", true);
						sendJsonResponse(resp, response);
						return;
					}
					
					if(year == null){
						year = thongKeDao.getLastedYearByStore(storeId);
					}
					
					if(index == null){
						index = "1";
					}
					
					int count = thongKeDao.countThongKeByStore(Integer.parseInt(year), storeId);
					int size = 10;
			        int endPage = count/size;
			        if(count % size !=0) {
			        	endPage++;
			        }
			        
			        Map<String, Object> map = new HashMap<>();
			        map.put("tag", index);
			        map.put("endPage", endPage);
			        
			        APIResponse<Map<String, Object>> response = new APIResponse<>("success", false, "",
			                map);
			        sendJsonResponse(resp, response);
				} else if (pathInfo.equals("/chart")){
					String store = req.getParameter("store");
					String year = req.getParameter("year");
					if(store == null || store.isEmpty()) {
						resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						APIResponse<String> response = new APIResponse<>("Không có param", true);
						sendJsonResponse(resp, response);
						return;
					}
					
					int storeId;
					
					try {
						storeId = Integer.parseInt(store);
					} catch (NumberFormatException e) {
						resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						APIResponse<String> response = new APIResponse<>("Sai format", true);
						sendJsonResponse(resp, response);
						return;
					}
					
					if(year == null){
						year = thongKeDao.getLastedYearByStore(storeId);
					}
					
					APIResponse<List<Chart>> response = new APIResponse<>("success", false, "chart",
							thongKeDao.getChartByStore(Integer.parseInt(year), storeId));
			        sendJsonResponse(resp, response);
				} else if (pathInfo.equals("/year-select")){
					String store = req.getParameter("store");
					if(store == null || store.isEmpty()) {
						resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						APIResponse<String> response = new APIResponse<>("Không có param", true);
						sendJsonResponse(resp, response);
						return;
					}
					
					int storeId;
					
					try {
						storeId = Integer.parseInt(store);
					} catch (NumberFormatException e) {
						resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						APIResponse<String> response = new APIResponse<>("Sai format", true);
						sendJsonResponse(resp, response);
						return;
					}
					
					APIResponse<List<String>> response = new APIResponse<>("success", false, "yearSelect",
							thongKeDao.getYearByStore(storeId));
			        sendJsonResponse(resp, response);
				}
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
