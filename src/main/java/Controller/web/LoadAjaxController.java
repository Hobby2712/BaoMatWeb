package Controller.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import DaoImpl.CategoryDAOImpl;
import DaoImpl.ProductDAOImpl;
import Entity.Product;

@WebServlet(urlPatterns = { "/ajax/load" })
public class LoadAjaxController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ProductDAO product = new ProductDAOImpl();
	CategoryDAO category = new CategoryDAOImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("X-Content-Type-Options", "nosniff");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		int count = product.countProduct();
		request.setAttribute("count", count);
		String amount = request.getParameter("exits");
		if (amount == null) {
			amount = "1";
		}
		
		int iamount = Integer.parseInt(amount);
		List<Product> list = product.getNext4Product(iamount);
		if (iamount < 12) {
			PrintWriter out = response.getWriter();
			for (Product o : list) {
				out.println("<div class=\"col-lg-3 col-md-4 col-sm-6 productP\">\r\n"
						+ "						<div class=\"featured__item\">\r\n"
						+ "							<div class=\"featured__item__pic set-bg\"\r\n"
						+ "								data-setbg=\"/Web/image?fname=" + o.getImage()
						+ "\"\" style=\"background-image: url(/Web/image?fname=" + o.getImage() + ");\">\r\n"
						+ "								<ul class=\"featured__item__pic__hover\">\r\n"
						+ "									<li><a href=\"addCart?pid=" + o.getId()
						+ "\"><i class=\"fa fa-shopping-cart\"></i></a></li>\r\n"
						+ "								</ul>\r\n" + "							</div>\r\n"
						+ "							<div class=\"featured__item__text\">\r\n"
						+ "								<h6>\r\n"
						+ "									<a href=\"detail?pid=" + o.getId() + "\">" + o.getName()
						+ "</a>\r\n" + "								</h6>\r\n"
						+ "								<h5>" + o.tienTe(o.getPrice()) + "</h5>\r\n"
						+ "							</div>\r\n" + "						</div>\r\n"
						+ "					</div>");
			}
		}
	}

}
