package Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Entity.User;

@WebFilter("/admin/*")
public class AdminAuthenticationFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
        // Khởi tạo filter
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("acc") == null) {
            resp.sendRedirect(req.getContextPath() + "/loginAccount");
            return;
        }

        User u = (User) session.getAttribute("acc");

        if (!(u.getRole() == 1)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập vào trang này!");
            return;
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
        // Hủy filter
    }
}
