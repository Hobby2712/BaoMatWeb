package Anti_ClickJacking;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class XFrameOptions implements Filter {

  public void init(FilterConfig config) throws ServletException {
    // Khởi tạo filter
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    httpResponse.setHeader("X-Frame-Options", "SAMEORIGIN");
    chain.doFilter(request, response);
  }

  public void destroy() {
    // Hủy filter
  }
}