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
@WebServlet(
		name = "home-servlet",
		description = "Servlet home",
		urlPatterns={"/home", "/login", "/logout"}
)
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserManager userManager = new UserManager();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (this.loginIfNot(request, response)) {
			
			final String uri = request.getRequestURI();
			
			if(uri.contains("/home")) {
				this.home(request, response);
			} else if (uri.contains("/login")) {
				this.login(request, response);
			} else if (uri.contains("/logout")) {
				this.logout(request, response);
			} else if (uri.contains("/test")) {
				this.test(request, response);
			} else {
				response.getWriter().append("Index");
			}
			response.getWriter().append("Served at: ").append(request.getContextPath());
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void home(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		User test = this.getCurrentUser(request);
		request.setAttribute("currentUser", test);
		request.getRequestDispatcher("/WEB-INF/html/home.jsp").forward(request, response);
		
		return;
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		final String username = request.getParameter("username");
		final String password = request.getParameter("password");
		
		if (username == null || password == null) {
			// just display the login
		} else if(this.userManager.checkLogin(username)) {
			if (this.userManager.checkLoginWithPassword(username, password)) {
				//request.setAttribute("success", "Login succeeded!");
				request.getSession().setAttribute("user", this.userManager.getUser(username));
				this.home(request, response);
				return;
			} else {
				request.setAttribute("errorMessage", "Bad password");
			}
		} else {
			request.setAttribute("errorMessage", "User not found");
		}	
		
		request.getRequestDispatcher("/WEB-INF/html/login.jsp").forward(request, response);
		return;
		
		
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getSession().removeAttribute("user");
		this.login(request, response);
	}
	
	private boolean loginIfNot(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		Object sessionUsername = request.getSession().getAttribute("user");
		
		if(sessionUsername == null){
			this.login(request, response);
			return false;
		} else {
			return true;
		}
	}
	
	private User getCurrentUser(HttpServletRequest request){
		return (User) request.getSession().getAttribute("user");
	}
	
	private void test(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
	}
	
	

}
