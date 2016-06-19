package fr.easypass.servlets;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;

import fr.easypass.manager.UserManagerDB;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "LoginServlet",  description = "Login Servlet", urlPatterns = {"/user/login", "/user/logout"})
public class LoginServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private UserManagerDB userManager = new UserManagerDB();
    
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
        
        if (uri.contains("/user/login")) {
            try {
				this.login(request, response);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
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

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

    	 HttpSession session = request.getSession();
         final String method = request.getMethod();

         if (method == "GET") {
             request.getRequestDispatcher("/WEB-INF/html/header.jsp").forward(request, response);
         } else {

        	   final String username = request.getParameter("username");
               final String email = request.getParameter("email");
               final String password = request.getParameter("password");


               Connection connection = (Connection) this.userManager.getConnection();
               PreparedStatement stmt;
               
               stmt = connection.prepareStatement("select * from users where username=?  and password=?");
               stmt.setString(1, email);
               stmt.setString(2, password);
               ResultSet rs = stmt.executeQuery();
               
               if (rs.next()) {
                   session.setAttribute("username", email);
                   session.setAttribute("alertClass", "alert-success");
                   session.setAttribute("alertMessage", "Vous êtes bien connect�");
                   response.sendRedirect("/easypass-j2e");
               } else {
                   session.setAttribute("alertClass", "alert-danger");
                   session.setAttribute("alertMessage", "inscription �chou�e");
               }
    }
    }
    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("/easypass-j2e");
    }

}
