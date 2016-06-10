package fr.easypass.servlets;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;

import org.apache.commons.lang.math.NumberUtils;

import fr.easypass.manager.UserManager;
import fr.easypass.model.User;
import fr.easypass.validation.formValidator;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(name = "UserServlet", description = "User Servlet", urlPatterns = { UserServlet.urlPrefix + "",
        UserServlet.urlPrefix + "/voir", UserServlet.urlPrefix + "/editer", "/inscription",
        UserServlet.urlPrefix + "/supprimer" })
public class UserServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;
    public static final String urlPrefix = "/admin/utilisateurs";
    public static final String baseURL = "/easypass" + urlPrefix;
    public static final String viewPathPrefix = "/WEB-INF/html/user";
    public final UserManager userManager = new UserManager();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
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

        if (uri.contains(urlPrefix + "/voir")) {
            this.show(request, response);
        } else if (uri.contains("/inscription")) {
            this.signUp(request, response);
        } else if (uri.contains(urlPrefix + "/editer")) {
            this.edit(request, response);
        } else if (uri.contains(urlPrefix + "/supprimer")) {
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

            final Integer success = this.userManager.insertUser(request);

            if (success == 1) {

                session.setAttribute("alertClass", "alert-success");
                session.setAttribute("alertMessage", "Vous êtes bien inscrit");

            } else {

                session.setAttribute("alertClass", "alert-danger");
                session.setAttribute("alertMessage", "L'inscription a échoué");
            }

            response.sendRedirect("/easypass");
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
