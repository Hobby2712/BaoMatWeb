package Filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/Web/home")
public class SessionFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // Kiểm tra xem phiên (session) có tồn tại hay không
        HttpSession session = request.getSession(false);

        // Nếu phiên không tồn tại, chuyển đến trang đăng nhập
        if (session == null) {
            response.sendRedirect("home");
            return;
        }

        // Lưu trữ Session ID trong Cookie
        String sessionId = session.getId();
        Cookie cookie = new Cookie("JSESSIONID", sessionId);
        cookie.setMaxAge(100);
        cookie.setHttpOnly(true);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
        // Khởi tạo bộ lọc
    }

    public void destroy() {
        // Hủy bỏ bộ lọc
    }
}