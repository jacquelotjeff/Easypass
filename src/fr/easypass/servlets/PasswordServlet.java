package fr.easypass.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.easypass.manager.PasswordManager;
import fr.easypass.model.Password;

/**
 * Servlet implementation class PasswordServlet
 */
@WebServlet(name = "PasswordServlet", description = "Password Servlet", urlPatterns = { PasswordServlet.urlPrefix + "/liste",
		PasswordServlet.urlPrefix + "/voir", PasswordServlet.urlPrefix + "/editer", PasswordServlet.urlPrefix + "/creer", PasswordServlet.urlPrefix + "/supprimer" })
public class PasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public static final String urlPrefix = "/user/password";
    public static final String baseURL = "/easypass" + urlPrefix;
    public static final String viewPathPrefix = "/WEB-INF/html/password";
    public final PasswordManager passwordManager = new PasswordManager();
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			final String uri = request.getRequestURI();
		        
		    if (uri.contains("/liste")) {
		        this.list(request, response);
		    } else if (uri.contains("/voir")) {
		        this.show(request, response);
		    } else if (uri.contains("/creer")) {
		        this.create(request, response);
	        } else if (uri.contains("/editer")) {
	            this.edit(request, response);
	        } else if (uri.contains("/supprimer")) {
	            this.delete(request, response);
	        } else {
	            response.getWriter().append("Index");
	        }
		}
	
		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			doGet(request, response);
		}
		
	
	    private void list(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	    	
	    	Map<Integer, Password> passwords = passwordManager.getPasswords();
	    	
	    	request.setAttribute("passwords", passwords.values());
	    	
	        request.getRequestDispatcher("/WEB-INF/html/password/list.jsp").forward(request, response);
	
	        return;
	    }
	
	    private void show(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	        
	        String passwordParam  = request.getParameter("password"); 
	        if (passwordParam == null) {
	            response.sendRedirect(PasswordServlet.baseURL + "/liste");
	            return;
	        }
	      
	        //Simulating database request  
	        final Password password = this.passwordManager.getPassword(passwordParam);
	        
	        if (password == null) {
	            response.sendRedirect(PasswordServlet.baseURL + "/liste");
	            return;
	        }
        
        request.setAttribute("password", password);
        request.getRequestDispatcher(PasswordServlet.viewPathPrefix + "/show.jsp").forward(request, response);

        return;
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        request.getRequestDispatcher(PasswordServlet.viewPathPrefix + "/create.jsp").forward(request, response);

        return;
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        String passwordParam  = request.getParameter("password"); 
        if (passwordParam == null) {
            response.sendRedirect(PasswordServlet.baseURL + "/liste");
            return;
        }
      
        //Simulating database request  
        final Password password = this.passwordManager.getPassword(passwordParam);
        
        if (password == null) {
            response.sendRedirect(PasswordServlet.baseURL + "/liste");
            return;
        }
        
        request.setAttribute("password", password);
        request.getRequestDispatcher(PasswordServlet.viewPathPrefix + "/edit.jsp").forward(request, response);

        return;
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       
        return;
    }

}
