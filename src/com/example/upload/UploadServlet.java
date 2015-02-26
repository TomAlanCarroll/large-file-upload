package com.example.upload;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet(urlPatterns = {"/upload"}, asyncSupported = true)
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
        maxFileSize=10000000000L,    // 10GB
        maxRequestSize=10000000000L) // 10GB
public class UploadServlet extends HttpServlet {
    /**
     * Accepts a file and saves it to the server directory
     * @param request A well-formed request
     * @param response A well-formed response
     * @throws ServletException If there is a problem modifying the response message
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long fileSize = 0L;
        try {
            for (Part part : request.getParts()) {
                part.write(extractFileName(part));
                fileSize = part.getSize();
            }
        } catch (Exception e) {
            request.setAttribute("message", "Unable to save file to disk.");
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        request.setAttribute("message", "Upload has completed! File size: " + (fileSize / 1000000000L) + "GB");
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    /**
     * Gets the filename for an uploaded file
     * @param part The file part to extract the filename from
     * @return The filename or "" if a filename cannot be extracted
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}
