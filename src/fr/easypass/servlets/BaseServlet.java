package fr.easypass.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static final String ENCODING = "utf-8";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING);
        response.setContentType("text/html;charset=" + ENCODING);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING);
        response.setContentType("text/html;charset=" + ENCODING);
    }
}
