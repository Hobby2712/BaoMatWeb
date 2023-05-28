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

@WebServlet(urlPatterns = { "/admin/ManagerCategory" })
public class ManagerCategoryController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	CategoryDAO cate = new CategoryDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("X-Content-Type-Options", "nosniff");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		String indexS = StringEscapeUtils.escapeHtml4(req.getParameter("index"));
		if (indexS == null) {
			indexS = "1";
		}
		int index = Integer.parseInt(indexS);

		int count = cate.countCategory();
		int size = 10;
		int endPage = count / size;
		if (count % size != 0) {
			endPage++;
		}

		List<Category> clist1 = cate.getAllCategory1();
		List<Category> clist2 = cate.getAllCategory2PT(index);
		
		req.setAttribute("cList", clist2);
		req.setAttribute("parentCategory", clist1);
		req.setAttribute("endPage", endPage);
        req.setAttribute("tag", index);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/CategoryManagement.jsp");
		dispatcher.forward(req, resp);
	}

}