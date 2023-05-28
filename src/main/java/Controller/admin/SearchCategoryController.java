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

import DAO.CategoryDAO;
import DaoImpl.CategoryDAOImpl;
import Entity.Category;

@WebServlet(urlPatterns = { "/admin/searchC" })
public class SearchCategoryController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	CategoryDAO dao = new CategoryDAOImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		String search = req.getParameter("txt").trim();
		String indexS = StringEscapeUtils.escapeHtml4(req.getParameter("index"));
		if (indexS == null) {
			indexS = "1";
		}

		int index = Integer.parseInt(indexS);

		if (search != null) {
			int count = dao.countSearch(search);
			int size = 10;
			int endPage = count / size;
			if (count % size != 0) {
				endPage++;
			}

			List<Category> clist1 = dao.getAllCategory1();
			List<Category> clist2 = dao.getAllCategory2Search(search,index);
			
			req.setAttribute("cList", clist2);
			req.setAttribute("parentCategory", clist1);
			req.setAttribute("endPage", endPage);
	        req.setAttribute("tag", index);
	        req.setAttribute("search", search);
		}
		else {
			int count = dao.countCategory();
			int size = 10;
			int endPage = count / size;
			if (count % size != 0) {
				endPage++;
			}

			List<Category> clist1 = dao.getAllCategory1();
			List<Category> clist2 = dao.getAllCategory2PT(index);
			
			req.setAttribute("cList", clist2);
			req.setAttribute("parentCategory", clist1);
			req.setAttribute("endPage", endPage);
	        req.setAttribute("tag", index);
	        req.setAttribute("search", search);
		}

		
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/SearchCategory.jsp");
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

		if (search != null) {
			int count = dao.countSearch(search);
			int size = 10;
			int endPage = count / size;
			if (count % size != 0) {
				endPage++;
			}

			List<Category> clist1 = dao.getAllCategory1();
			List<Category> clist2 = dao.getAllCategory2Search(search,index);
			
			req.setAttribute("cList", clist2);
			req.setAttribute("parentCategory", clist1);
			req.setAttribute("endPage", endPage);
	        req.setAttribute("tag", index);
	        req.setAttribute("search", search);
		}
		else {
			int count = dao.countCategory();
			int size = 10;
			int endPage = count / size;
			if (count % size != 0) {
				endPage++;
			}

			List<Category> clist1 = dao.getAllCategory1();
			List<Category> clist2 = dao.getAllCategory2PT(index);
			
			req.setAttribute("cList", clist2);
			req.setAttribute("parentCategory", clist1);
			req.setAttribute("endPage", endPage);
	        req.setAttribute("tag", index);
	        req.setAttribute("search", search);
		}

		
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/SearchCategory.jsp");
		dispatcher.forward(req, resp);
	}

}