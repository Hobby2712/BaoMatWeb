package Controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.UserDAO;
import DaoImpl.UserDAOImpl;
import Entity.User;

@WebServlet(urlPatterns = { "/admin/search" })
public class SearchAccountController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	UserDAO user = new UserDAOImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		String search = req.getParameter("txt").trim();
		String indexS = req.getParameter("index");
		if (indexS == null) {
			indexS = "1";
		}

		int index = Integer.parseInt(indexS);

		if (search != null) {
			List<User> alist = user.searchByName(search, index);
			int count = user.countSearch(search);
			int size = 10;
			int endPage = count / size;
			if (count % size != 0) {
				endPage++;
			}
			req.setAttribute("aList", alist);
			req.setAttribute("search", search);
			req.setAttribute("endPage", endPage);
			req.setAttribute("tag", index);
		}
		else {
			List<User> alist = user.getAll(index);
			int count = user.countAccount();
	        int size = 10;
	        int endPage = count/size;
	        if(count % size !=0) {
	        	endPage++;
	        }
	        req.setAttribute("aList", alist);
	        req.setAttribute("search", search);
			req.setAttribute("endPage", endPage);
			req.setAttribute("tag", index);
		}

		
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/SearchAccount.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		String search = req.getParameter("txt").trim();
		String indexS = req.getParameter("index");
		if (indexS == null) {
			indexS = "1";
		}

		int index = Integer.parseInt(indexS);

		if (search != "") {
			List<User> alist = user.searchByName(search, index);
			int count = user.countSearch(search);
			int size = 10;
			int endPage = count / size;
			if (count % size != 0) {
				endPage++;
			}
			req.setAttribute("aList", alist);
			req.setAttribute("search", search);
			req.setAttribute("endPage", endPage);
			req.setAttribute("tag", index);
			System.out.print("a"+search+"a");
		}
		else {
			List<User> alist = user.getAll(index);
			int count = user.countAccount();
	        int size = 10;
	        int endPage = count/size;
	        if(count % size !=0) {
	        	endPage++;
	        }
	        req.setAttribute("aList", alist);
	        req.setAttribute("search", search);
			req.setAttribute("endPage", endPage);
			req.setAttribute("tag", index);
		}

		
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/SearchAccount.jsp");
		dispatcher.forward(req, resp);
	}

}