package fr.easypass.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;

import fr.easypass.manager.UserManager;
import fr.easypass.model.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(name = "UserServlet", description = "User Servlet", urlPatterns = { UserServlet.prefixURL + "",
        UserServlet.prefixURL + "/voir", UserServlet.prefixURL + "/editer", "/inscription",
        UserServlet.prefixURL + "/supprimer" })
public class UserServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;
    public static final String prefixURL = "/admin/utilisateurs";
    public static String baseURL;
    public static String rootPath;
    public static final String viewPathPrefix = "/WEB-INF/html/user";
    public final UserManager userManager = new UserManager();
	private HashMap<String, String> errors;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
        super.init();
        UserServlet.rootPath = this.getServletContext().getContextPath();
        UserServlet.baseURL = UserServlet.rootPath + UserServlet.prefixURL;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        super.doGet(request, response);

        final String uri = request.getRequestURI();

        if (uri.contains(prefixURL + "/voir")) {
            this.show(request, response);
        } else if (uri.contains("/inscription")) {
            this.signUp(request, response);
        } else if (uri.contains(prefixURL + "/editer")) {
            this.edit(request, response);
        } else if (uri.contains(prefixURL + "/supprimer")) {
            this.delete(request, response);
        } else {
            this.list(request, response);
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

    /**
     * Action for listing users
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void list(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Get the users from manager, and passing variable to the list.jsp view
        final Map<Integer, User> users = userManager.getUsers();

        request.setAttribute("users", users.values());

        request.getRequestDispatcher("/WEB-INF/html/user/list.jsp").forward(request, response);

        // Do not forget the return at the end of action
        return;
    }

    /**
     * Display a user profile
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void show(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Integer userId = this.checkUserParam(request, response);
        
        if (userId>0) {
            
            final User user = this.userManager.getUser(userId);

            if (user == null) {
                this.alertUserNotFound(request, response);
            } else {
                request.setAttribute("user", user);
                request.getRequestDispatcher(UserServlet.viewPathPrefix + "/show.jsp").forward(request, response);
                return;
            }
            
        }
        response.sendRedirect(UserServlet.baseURL);
        
        return;
    }

    private void signUp(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        final String method = request.getMethod();

        if (method == "GET") {
            request.setAttribute("formAction", "inscription");
            request.getRequestDispatcher(UserServlet.viewPathPrefix + "/signUp.jsp").forward(request, response);
        } else {
            User u = new User(
                    request.getParameter("firstname"),
                    request.getParameter("lastname"),
                    request.getParameter("username"),
                    request.getParameter("password"),
                    request.getParameter("email")
                    );
            errors= u.isValid();
            if(errors.isEmpty()){
            final Integer success = this.userManager.insertUser(request);
                if (success == 1) {
                    session.setAttribute("alertClass", "alert-success");
                    session.setAttribute("alertMessage", "Vous êtes bien inscrit");
                } else {
                    session.setAttribute("alertClass", "alert-danger");
                    session.setAttribute("alertMessage", "L'inscription a échoué");
                }
                response.sendRedirect("/easypass");
            } else {
                request.setAttribute("errors", errors);
                request.getRequestDispatcher(UserServlet.viewPathPrefix + "/signUp.jsp").forward(request, response);
            }
        }
        return;

    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        final String method = request.getMethod();
        
        Integer userId = this.checkUserParam(request, response);
        
        if (userId > 0) {
            if (method == "GET") {

                final User user = this.userManager.getUser(userId);

                if (user == null) {
                    this.alertUserNotFound(request, response);
                } else {
                    request.setAttribute("user", user);
                    request.setAttribute("formAction", "editer");
                    request.getRequestDispatcher(UserServlet.viewPathPrefix + "/edit.jsp").forward(request, response);
                    return;
                }
                
            } else {
                
                String username = request.getParameter("username");
                String lastname = request.getParameter("lastname");
                String firstname = request.getParameter("firstname");
                String password = request.getParameter("password");
                String email = request.getParameter("email");

                final Integer success = this.userManager.editUser(userId, username, lastname, firstname, password, email);

                if (success == 1) {
                    session.setAttribute("alertClass", "alert-success");
                    session.setAttribute("alertMessage", "L'utilisateur a bien été édité.");
                    
                } else {
                    session.setAttribute("alertClass", "alert-danger");
                    session.setAttribute("alertMessage", "L'utilisateur n'a pas pu être édité.");
                }
            }
        }
        
        response.sendRedirect(UserServlet.baseURL);
        return;
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        final String method = request.getMethod();

        if (method == "POST") {
            
            Integer userId = this.checkUserParam(request, response);
            if (userId > 0) {
                
                final Integer success = this.userManager.deleteUser(userId);

                if (success == 0) {
                    session.setAttribute("alertClass", "alert-danger");
                    session.setAttribute("alertMessage", "L'utilisateur n'a pas pu être supprimé.");
                } else {
                    session.setAttribute("alertClass", "alert-success");
                    session.setAttribute("alertMessage", "L'utilisateur a bien été supprimé.");
                }
                
            }

        } else {
            session.setAttribute("alertClass", "alert-danger");
            session.setAttribute("alertMessage", "Accès interdit");
        }

        response.sendRedirect(UserServlet.baseURL);

        return;
    }

    private Integer checkUserParam(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        Integer userId = 0;
        
        try {
            userId = NumberUtils.createInteger(request.getParameter("userId"));
        } catch (Exception e) {
            this.alertUserNotFound(request, response);
        }

        return userId;
    }

    private void alertUserNotFound(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("alertClass", "alert-danger");
        session.setAttribute("alertMessage", "L'utilisateur n'a pas été trouvé.");

        return;

    }

}
