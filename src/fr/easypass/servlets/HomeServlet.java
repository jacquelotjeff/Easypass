package fr.easypass.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.easypass.db.UserManager;
import fr.easypass.model.User;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet(name = "HomeServlet", description = "Home Servlet", urlPatterns = {"", "/admin"})
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final String uri = request.getRequestURI();

        if (uri.contains("/admin")) {
            this.admin(request, response);
        } else {
            this.home(request, response);
            response.getWriter().append("Index");
        }
        response.getWriter().append("Served at: ").append(request.getContextPath());

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

    private void home(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //User test = this.getCurrentUser(request);
        //request.setAttribute("currentUser", test);
        request.getRequestDispatcher("/WEB-INF/html/home/home.jsp").forward(request, response);

        return;
    }
    
    private void admin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //User test = this.getCurrentUser(request);
        //request.setAttribute("currentUser", test);
        request.getRequestDispatcher("/WEB-INF/html/home/admin.jsp").forward(request, response);

        return;
    }

}
