package Controller.seller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;

import DAO.ProductDAO;
import DaoImpl.ProductDAOImpl;

@WebServlet(urlPatterns = {"/seller/del"})
public class DeleteProductController extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	response.setContentType("text/html");
    	response.setCharacterEncoding("UTF-8");
    	request.setCharacterEncoding("UTF-8");
        int pid = Integer.parseInt(StringEscapeUtils.escapeHtml4(request.getParameter("pid")));
        ProductDAO dao = new ProductDAOImpl();
        dao.deleteProduct(pid);
        response.sendRedirect("managerP");
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
