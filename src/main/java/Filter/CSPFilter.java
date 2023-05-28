package Filter;

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
public class CSPFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		// Không cần thiết phải làm gì trong phương thức này
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'unsafe-inline' 'unsafe-eval' *; style-src 'self' 'unsafe-inline' https://fonts.googleapis.com/; img-src 'self' data:; font-src 'self'  https://fonts.gstatic.com/");
		chain.doFilter(request, response);
	}

	public void destroy() {
		// Không cần thiết phải làm gì trong phương thức này
	}
}