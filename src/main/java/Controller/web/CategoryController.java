/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.web;

import Entity.Category;
import Entity.Product;
import Entity.User;
import Util.Constant;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.CartDAO;
import DAO.CategoryDAO;
import DAO.ProductDAO;
import DaoImpl.CartDAOImpl;
import DaoImpl.CategoryDAOImpl;
import DaoImpl.ProductDAOImpl;


@WebServlet(urlPatterns = {"/category"})
public class CategoryController extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CartDAO cart = new CartDAOImpl();
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	String cookieHeader = String.format("JSESSIONID=%s; %s", req.getSession().getId(), Constant.sameSite);
		resp.setHeader("Set-Cookie", cookieHeader);
    	resp.setHeader("X-Content-Type-Options", "nosniff");
    	resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		
		//header .......................................................
		
        String cateID = req.getParameter("cid");
        String cateID2 = req.getParameter("cid2");
        
    	CategoryDAO category = new CategoryDAOImpl();
    	ProductDAO product = new ProductDAOImpl();
        List<Category> listC = category.getAllCategory1();
        List<Category> clist2 = category.getAllCategory2();
        
        List<Product> plist3lastest = product.get3LastestProduct();
		List<Product> plist3_6lastest = product.get3_6LastestProduct();
		req.setAttribute("pList3Lastest", plist3lastest);
		req.setAttribute("pList3_6Lastest", plist3_6lastest);

        
        if(cateID==null)
        {
        	List<Product> list2 = product.getProductByCID2(cateID2);
        	req.setAttribute("pList", list2);
        }
        else {
        	List<Product> list = product.getProductByCID(cateID);
        	req.setAttribute("pList", list);
        }
        req.setAttribute("cList", listC);
        req.setAttribute("cList2", clist2);
        req.setAttribute("tag", cateID);
        
        HttpSession session = req.getSession();
		User u = (User) session.getAttribute("acc");

		if (u != null) {
			int countCart = cart.countCart(cart.getCartIdByUId(u.getId()));
			req.setAttribute("countC", countCart);
		}

        
        req.getRequestDispatcher("/views/web/shop-grid.jsp").forward(req, resp);
      //header .......................................................
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
