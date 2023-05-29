package Controller.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;

import DAO.CartDAO;
import DaoImpl.CartDAOImpl;
import Entity.User;
import Util.Constant;

@WebServlet(urlPatterns = {"/deleteCart"})
public class DeleteCartController extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String cookieHeader = String.format("JSESSIONID=%s; %s", request.getSession().getId(), Constant.sameSite);
		response.setHeader("Set-Cookie", cookieHeader);
    	response.setHeader("X-Content-Type-Options", "nosniff");
    	response.setContentType("text/html");
    	response.setCharacterEncoding("UTF-8");
    	request.setCharacterEncoding("UTF-8");
    	
    	HttpSession session = request.getSession();
		User u = (User) session.getAttribute("acc");
		
        int pid = Integer.parseInt(StringEscapeUtils.escapeHtml4(request.getParameter("pid")));
        
        CartDAO dao = new CartDAOImpl();
        dao.deleteItemCart(dao.getCartIdByUId(u.getId()),pid);
        response.sendRedirect("cart");
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
