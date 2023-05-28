package Filter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StaticFile extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        // Kiểm tra đường dẫn của tệp tin và thiết lập tiêu đề X-Content-Type-Options nếu là file Bootstrap
        if (pathInfo != null && pathInfo.startsWith("/template/")) {
            response.setHeader("X-Content-Type-Options", "nosniff");
        }
        // Phục vụ tệp tin tĩnh
        // ...
    }
    // Các phương thức khác...
}
