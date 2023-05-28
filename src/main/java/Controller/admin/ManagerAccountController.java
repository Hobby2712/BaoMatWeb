package Controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.text.StringEscapeUtils;

import DAO.UserDAO;
import DaoImpl.UserDAOImpl;
import Entity.User;

@WebServlet(urlPatterns = { "/admin/ManagerAccount" })
public class ManagerAccountController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	UserDAO user = new UserDAOImpl() {
	};

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		
		String indexS = StringEscapeUtils.escapeHtml4(req.getParameter("index"));
		if(indexS==null) {
			indexS="1";
		}
		
		int index = Integer.parseInt(indexS);
		
		int count = user.countAccount();
        int size = 10;
        int endPage = count/size;
        if(count % size !=0) {
        	endPage++;
        }
        
		List<User> alist = user.getAll(index);
		req.setAttribute("aList", alist);
		
		req.setAttribute("endPage", endPage);
        req.setAttribute("tag", index);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/AccountManagement.jsp");
		dispatcher.forward(req, resp);
	}

}