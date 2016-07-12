package fr.easypass.servlets.front;

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

import fr.easypass.manager.CategoryManager;
import fr.easypass.manager.GroupManager;
import fr.easypass.manager.PasswordManager;
import fr.easypass.manager.UserManager;
import fr.easypass.model.Category;
import fr.easypass.model.Group;
import fr.easypass.model.Password;
import fr.easypass.model.User;
import fr.easypass.servlets.BaseServlet;
import fr.easypass.servlets.LoginServlet;

/**
 * Servlet implementation class PasswordServlet
 */
@WebServlet(name = "FrontPasswordServlet", description = "Front Password Servlet", urlPatterns = {
        FrontPasswordServlet.URL_BASE, FrontPasswordServlet.URL_BASE + "/voir",
        FrontPasswordServlet.URL_BASE + "/editer", FrontPasswordServlet.URL_BASE + "/creer",
        FrontPasswordServlet.URL_BASE + "/supprimer" })
public class FrontPasswordServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private HashMap<String, String> errors;

    public static final String URL_BASE = "/utilisateur/mots-de-passes";
    public static final String viewPathPrefix = "/WEB-INF/html/front/password";
    public final PasswordManager passwordManager = new PasswordManager();
    public final GroupManager groupManager = new GroupManager();
    public final UserManager userManager = new UserManager();
    public final CategoryManager categoryManager = new CategoryManager();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontPasswordServlet() {
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

        if (uri.contains("/voir")) {
            this.show(request, response);
        } else if (uri.contains("/creer")) {
            this.create(request, response);
        } else if (uri.contains("/editer")) {
            this.edit(request, response);
        } else if (uri.contains("/supprimer")) {
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
    
    private void list(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Integer userId = LoginServlet.getCurrentUser(request).getId();

        final User user = this.userManager.getUser(userId);

        Map<Integer, Password> passwords = this.passwordManager.getPasswordsByUser(userId);
        Map<Integer, Category> categories = this.categoryManager.getCategories();

        request.setAttribute("passwords", passwords.values());
        request.setAttribute("categories", categories);
        request.setAttribute("user", user);
        request.getRequestDispatcher(FrontPasswordServlet.viewPathPrefix + "/list.jsp").forward(request, response);

        return;
    }
    
    private void show(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Integer passwordId = this.checkPasswordParam(request, response);

        if (passwordId > 0) {

            final Group group = this.groupManager.getGroup(passwordId);

            if (group == null) {
                this.alertPasswordNotFound(request, response);
            } else {

                request.setAttribute("group", group);
                request.getRequestDispatcher(FrontPasswordServlet.viewPathPrefix + "/show.jsp").forward(request, response);

                return;
            }
        }

        response.sendRedirect(this.getServletContext().getContextPath() + FrontPasswordServlet.URL_BASE);
        return;
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        String method = request.getMethod();

        Integer ownerId = this.checkOwnerParam(request, response);
        String ownerType = request.getParameter("ownerType");

        if (!ownerType.equals(Password.OWNER_TYPE_GROUP) && !ownerType.equals(Password.OWNER_TYPE_USER)) {
            session.setAttribute("alertClass", "alert-danger");
            session.setAttribute("alertMessage", "Cette page n'existe pas.");
            response.sendRedirect(this.getServletContext().getContextPath() + FrontPasswordServlet.URL_BASE);
            return;
        }

        if (method == "POST") {

            String title = request.getParameter("title");
            String site = request.getParameter("site");
            String password = request.getParameter("password");
            Integer categoryId = NumberUtils.createInteger(request.getParameter("categoryId"));
            String informations = request.getParameter("informations");

            Password pwd = new Password(title, site, password, informations, categoryId);

            errors = pwd.isValid();
            if (errors.isEmpty()) {
                Integer success = this.passwordManager.insertPassword(title, site, password, categoryId, informations,
                        ownerId, ownerType);

                if (success == 1) {

                    session.setAttribute("alertClass", "alert-success");
                    session.setAttribute("alertMessage", "Le mot de passe a bien été ajouté.");

                } else {

                    session.setAttribute("alertClass", "alert-danger");
                    session.setAttribute("alertMessage", "Le mot de passe n'a pas pu être ajouté.");
                }
                
                response.sendRedirect(this.getServletContext().getContextPath() + FrontPasswordServlet.URL_BASE);

                return;

            } else {

                request.setAttribute("errors", errors);
            }

        }

        Map<Integer, Category> categories = this.categoryManager.getCategories();

        request.setAttribute("formAction", "creer");
        request.setAttribute("categories", categories);
        request.setAttribute("ownerId", ownerId);
        request.setAttribute("ownerType", ownerType);
        request.getRequestDispatcher(FrontPasswordServlet.viewPathPrefix + "/create.jsp").forward(request, response);

        return;

    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Integer passwordId = this.checkPasswordParam(request, response);
        final String method = request.getMethod();
        HttpSession session = request.getSession();
        
        if (passwordId > 0) {
            
            if (method == "GET") {
                
                Password password = this.passwordManager.getPassword(passwordId);
                Map<Integer, Category> categories = this.categoryManager.getCategories();
                
                request.setAttribute("password", password);
                request.setAttribute("categories", categories);
                
                request.getRequestDispatcher(FrontPasswordServlet.viewPathPrefix + "/edit.jsp").forward(request, response);
                
                return;
                
            } else {
                
                String title = request.getParameter("title");
                Integer category = NumberUtils.createInteger(request.getParameter("categoryId"));
                String site = request.getParameter("site");
                String plainPassword = request.getParameter("password");
                String informations = request.getParameter("informations");
                
                Password password = new Password(title, site, plainPassword, informations, category);
                
                errors = password.isValid();
                
                if (errors.isEmpty()) {
                    
                    final Integer success = this.passwordManager.editPassword(passwordId, title, site, plainPassword, category, informations);
                    
                    if (success == 1) {
                        session.setAttribute("alertClass", "alert-success");
                        session.setAttribute("alertMessage", "Le mot de passe a bien été édité.");

                    } else {
                        session.setAttribute("alertClass", "alert-danger");
                        session.setAttribute("alertMessage", "Le mot de passe n'a pas pu être édité.");
                    }
                    
               } else {
                   request.setAttribute("errors", errors);
               }
            }
        }
        
        response.sendRedirect(this.getServletContext().getContextPath() + FrontPasswordServlet.URL_BASE);

        return;
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        return;
    }

    private Integer checkOwnerParam(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Integer ownerId = 0;

        try {
            ownerId = NumberUtils.createInteger(request.getParameter("ownerId"));
        } catch (Exception e) {
            this.alertOwnerNotFound(request);
        }

        return ownerId;
    }

    private Integer checkPasswordParam(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Integer passwordId = 0;

        try {
            passwordId = NumberUtils.createInteger(request.getParameter("passwordId"));
        } catch (Exception e) {
            this.alertPasswordNotFound(request, response);
        }

        return passwordId;
    }

    private void alertPasswordNotFound(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("alertClass", "alert-danger");
        session.setAttribute("alertMessage", "Le mot de passe n'a pas été trouvé.");

        return;

    }

    private void alertOwnerNotFound(HttpServletRequest request) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("alertClass", "alert-danger");
        session.setAttribute("alertMessage", "Le propriétaire n'a pas été trouvé.");

        return;

    }

}
