package Csrf;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import Util.CsrfTokenUtil;

public class CsrfTokenSessionListener implements HttpSessionListener {
    public void sessionCreated(HttpSessionEvent event) {
        // Tạo csrf_token mới
        String csrfToken = CsrfTokenUtil.generateCsrfToken();
        // Lưu trữ csrf_token trong session
        event.getSession().setAttribute("csrf_token", csrfToken);
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        // Xóa csrf_token khỏi session khi session bị hủy
        event.getSession().removeAttribute("csrf_token");
    }
}
