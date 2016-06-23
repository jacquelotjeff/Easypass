package fr.easypass.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.easypass.manager.UserManager;
import fr.easypass.model.User;

/**
 * Servlet implementation class LoginServlet
 */

@WebServlet(name = "LoginServlet", description = "Login Servlet", urlPatterns = { "/user/login", "/user/logout" })
public class LoginServlet extends BaseServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        super.doGet(request, response);
        
        final String uri = request.getRequestURI();

        if (uri.contains("/user/login")) {
            this.login(request, response);
        } else if (uri.contains("/user/logout")) {
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

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        final String method = request.getMethod();
        
        if (method == "GET") {
            
            request.getRequestDispatcher("/WEB-INF/html/header.jsp").forward(request, response);
            
        } else {

            final String email = request.getParameter("email");
            final String password = request.getParameter("password");
            
            User user = userManager.checkMailWithPassword(email, password);


            if (user instanceof User) {
                session.setAttribute("username", user.getUsername());
                session.setAttribute("alertClass", "alert-success");
                session.setAttribute("alertMessage", "Vous êtes bien connecté.");
            } else {
                session.setAttribute("alertClass", "alert-danger");
                session.setAttribute("alertMessage", "Connexion échouée.");
            }
            
            response.sendRedirect("/easypass");
        }
        
        return;
        
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("/easypass");
    }

}
