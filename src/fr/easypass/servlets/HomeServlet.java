package fr.easypass.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet(name = "HomeServlet", description = "Home Servlet", urlPatterns = {"", "/admin"})
public class HomeServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        super.doGet(request, response);

        final String uri = request.getRequestURI();
        
        if (uri.contains("/admin")) {
            this.admin(request, response);
        } else {
            this.home(request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void home(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //User test = this.getCurrentUser(request);
        request.getRequestDispatcher("/WEB-INF/html/front/home.jsp").forward(request, response);

        return;
    }
    
    private void admin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //User test = this.getCurrentUser(request);
        //request.setAttribute("currentUser", test);
        request.getRequestDispatcher("/WEB-INF/html/back/home.jsp").forward(request, response);

        return;
    }

}
