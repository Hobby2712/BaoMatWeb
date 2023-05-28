package Controller.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
    private static final String UPLOADS_DIRECTORY = "/uploads";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("fname");
        if (fileName == null) {
            return;
        }

        // Giới hạn ký tự đặc biệt và xử lý escape
        fileName = StringEscapeUtils.escapeHtml4(fileName);
      

        File file = new File(Constant.DIR, fileName);

        // Kiểm tra xem tệp có nằm trong thư mục tải lên hợp lệ không
        if (!isValidFilePath(file)) {
            return;
        }

        resp.setContentType("image/jpeg");
        IOUtils.copy(new FileInputStream(file), resp.getOutputStream());
    }


    private boolean isValidFilePath(File file) {
        // Kiểm tra xem tệp có nằm trong thư mục tải lên hợp lệ không
        String absolutePath = file.getAbsolutePath();
        String uploadsDirectoryPath = new File(Constant.DIR, UPLOADS_DIRECTORY).getAbsolutePath();
        return absolutePath.startsWith(uploadsDirectoryPath);
    }
}
