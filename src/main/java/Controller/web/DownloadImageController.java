package Controller.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringEscapeUtils;

import Util.Constant;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/image") // ?fname=abc.png
public class DownloadImageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    	resp.setHeader("X-Content-Type-Options", "nosniff");

        String fileName = StringEscapeUtils.escapeHtml4(req.getParameter("fname"));
        Path filePath = Paths.get(Constant.DIR, fileName).normalize();
        
        File file = filePath.toFile();
        String canonicalPath = file.getCanonicalPath();
        
        if (!filePath.startsWith(Paths.get(Constant.DIR).toAbsolutePath()) || canonicalPath.endsWith("WEB-INF" + File.separator + "web.xml")) {
            // Đường dẫn tệp không nằm trong thư mục hợp lệ hoặc là tệp WEB-INF/web.xml
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        if (file.exists() && file.isFile()) {
            resp.setContentType("image/jpeg");
            IOUtils.copy(new FileInputStream(file), resp.getOutputStream());
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}