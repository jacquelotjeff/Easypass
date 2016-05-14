package fr.easypass.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.easypass.db.UserManager;
import fr.easypass.model.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(name = "UserServlet", description = "User Servlet", urlPatterns = { UserServlet.urlPrefix + "/liste",
        UserServlet.urlPrefix + "/voir", UserServlet.urlPrefix + "/editer", UserServlet.urlPrefix + "/creer", UserServlet.urlPrefix + "/supprimer" })
public class UserServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    public static final String urlPrefix = "/admin/utilisateurs";
    public static final String baseURL = "/easypass" + urlPrefix;
    public static final String viewPathPrefix = "/WEB-INF/html/user";
    public final UserManager userManager = new UserManager();
    
    public HashMap<String, String> routes = new HashMap<>();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        routes.put("list", UserServlet.urlPrefix + "/liste");
        routes.put("create", UserServlet.urlPrefix + "/creer");
        routes.put("show", UserServlet.urlPrefix + "/voir");
        routes.put("edit", UserServlet.urlPrefix + "/editer");
        routes.put("delete", UserServlet.urlPrefix + "/supprimer");
    }
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
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
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

    private void list(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/html/user/list.jsp").forward(request, response);

        return;
    }

    private void show(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        if (request.getParameter("userId") == null) {
            response.sendRedirect(UserServlet.baseURL + "/liste");
            return;
        }
      
        final Integer userId = Integer.parseInt(request.getParameter("userId"));
        //Simulating database request  
        final User user = this.userManager.getUser(userId.toString());
        request.setAttribute("user", user);
        request.getRequestDispatcher(UserServlet.viewPathPrefix + "/show.jsp").forward(request, response);

        return;
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        request.getRequestDispatcher(UserServlet.viewPathPrefix + "/create.jsp").forward(request, response);

        return;
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        request.getRequestDispatcher(UserServlet.viewPathPrefix + "/edit.jsp").forward(request, response);

        return;
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //request.getRequestDispatcher("/WEB-INF/html/user/list.jsp").forward(request, response);

        return;
    }

}
