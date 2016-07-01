package fr.easypass.servlets.back;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;

import fr.easypass.manager.CategoryManager;
import fr.easypass.manager.GroupManager;
import fr.easypass.manager.PasswordManager;
import fr.easypass.manager.UserManager;
import fr.easypass.model.Category;
import fr.easypass.model.Group;
import fr.easypass.model.Password;
import fr.easypass.model.User;
import fr.easypass.servlets.BaseServlet;
import fr.easypass.servlets.front.FrontUserServlet;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(name = "BackUserServlet", description = "User Servlet", urlPatterns = { BackUserServlet.prefixURL + "",
        BackUserServlet.prefixURL + "/voir", BackUserServlet.prefixURL + "/editer", "/inscription",
        BackUserServlet.prefixURL + "/supprimer" })
public class BackUserServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;
    public static final String prefixURL = "/admin/utilisateurs";
    public static final String viewPathPrefix = "/WEB-INF/html/back/user";
    public final UserManager userManager = new UserManager();
    public final GroupManager groupManager = new GroupManager();
    public final PasswordManager passwordManager = new PasswordManager();
    public final CategoryManager categoryManager = new CategoryManager();
	private HashMap<String, String> errors;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BackUserServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
        super.init();
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

        request.getRequestDispatcher(BackUserServlet.viewPathPrefix + "/list.jsp").forward(request, response);

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

                Map<String, Map<Integer, Group>> groups = this.groupManager.getGroupByUsers(userId);
            	Map<Integer, Password> passwords = this.passwordManager.getPasswordsByUser(userId);
            	Map<Integer, Category> categories = this.categoryManager.getCategories();
            	
            	request.setAttribute("groups", groups.get("groups").values());
            	request.setAttribute("groupsAdmin", groups.get("groupsAdmin"));
            	request.setAttribute("passwords", passwords.values());
                request.setAttribute("categories", categories);
                request.setAttribute("user", user);
                request.getRequestDispatcher(BackUserServlet.viewPathPrefix + "/show.jsp").forward(request, response);
                return;
            }
            
        }
        response.sendRedirect(this.getServletContext().getContextPath() + BackUserServlet.prefixURL);
        
        return;
    }

    private void signUp(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        final String method = request.getMethod();

        if (method == "GET") {
            request.setAttribute("formAction", "inscription");
            request.getRequestDispatcher(FrontUserServlet.viewPathPrefix + "/signUp.jsp").forward(request, response);
        } else {
            
            User user = this.createUserFromParam(request);
            user.setAdmin(false);
            errors = user.isValid();
            
            if(errors.isEmpty()){
                
                final Integer success = this.userManager.insertUser(user);
            
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
                request.getRequestDispatcher(FrontUserServlet.viewPathPrefix + "/signUp.jsp").forward(request, response);
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
                    request.getRequestDispatcher(BackUserServlet.viewPathPrefix + "/edit.jsp").forward(request, response);
                    return;
                }
                
            } else {
                
                User user = this.createUserFromParam(request);
                
                errors = user.isValid();
                
                if (errors.isEmpty()) {
                    
                    final Integer success = this.userManager.editUser(userId, user);

                    if (success == 1) {
                        session.setAttribute("alertClass", "alert-success");
                        session.setAttribute("alertMessage", "L'utilisateur a bien été édité.");
                        
                    } else {
                        session.setAttribute("alertClass", "alert-danger");
                        session.setAttribute("alertMessage", "L'utilisateur n'a pas pu être édité.");
                    }
                    
                } else {
                    request.setAttribute("errors", errors);
                    request.getRequestDispatcher(BackUserServlet.viewPathPrefix + "/signUp.jsp").forward(request, response);
                }
            }
        }
        
        response.sendRedirect(this.getServletContext().getContextPath() + BackUserServlet.prefixURL);
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

        response.sendRedirect(this.getServletContext().getContextPath() + BackUserServlet.prefixURL);

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
    
    private User createUserFromParam(HttpServletRequest request) throws IOException {

        Boolean admin = BooleanUtils.toBooleanObject(request.getParameter("admin"));
        
        if (admin == null) {
            admin = false;
        }
        
        User user = new User(
            request.getParameter("firstname"),
            request.getParameter("lastname"),
            request.getParameter("username"),
            request.getParameter("password"),
            request.getParameter("email"),
            admin
        );
        
        return user;
        
    }

    private void alertUserNotFound(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("alertClass", "alert-danger");
        session.setAttribute("alertMessage", "L'utilisateur n'a pas été trouvé.");

        return;

    }

}
