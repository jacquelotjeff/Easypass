package fr.easypass.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    public static String rootPath;
    public static final String ENCODING = "utf-8";
    
    @Override
    public void init() throws ServletException {
        super.init();
        BaseServlet.rootPath = this.getServletContext().getContextPath();
    }
    
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
