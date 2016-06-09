package fr.easypass.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.easypass.manager.UserManager;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "LoginServlet",  description = "Login Servlet", urlPatterns = {"/login", "/logout"})
public class LoginServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private UserManager userManager = new UserManager();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
        
        if (uri.contains("/login")) {
            this.login(request, response);
        } else if (uri.contains("/logout")) {
            this.logout(request, response);
        } else {
            response.getWriter().append("No route LoginServlet");
        }
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

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        final String username = request.getParameter("username");
        final String password = request.getParameter("password");
        
        //TODO Use https://docs.oracle.com/javaee/7/tutorial/security-intro005.htm#BNBXM for Login
        //request.login(username, password);

        request.getRequestDispatcher("/WEB-INF/html/login/login.jsp").forward(request, response);
        return;

    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //this.login(request, response);
    }

}
