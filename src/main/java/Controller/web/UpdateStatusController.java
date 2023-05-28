package Controller.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;

import DAO.OrderDAO;
import DaoImpl.OrderDAOImpl;

@WebServlet(urlPatterns = { "/updateStatusOrder" })
public class UpdateStatusController extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	OrderDAO dao = new OrderDAOImpl();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setContentType("text/html");
    	response.setCharacterEncoding("UTF-8");
    	request.setCharacterEncoding("UTF-8");
		
        int id = Integer.parseInt(StringEscapeUtils.escapeHtml4(request.getParameter("id")));
        int status = Integer.parseInt(StringEscapeUtils.escapeHtml4(request.getParameter("status")));
        
        dao.updateOrderStatus(id, status);
        response.sendRedirect("order");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
