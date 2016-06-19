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

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;

import fr.easypass.manager.CategoryManager;
import fr.easypass.manager.GroupManager;
import fr.easypass.manager.PasswordManager;
import fr.easypass.manager.UserManager;
import fr.easypass.model.Category;
import fr.easypass.model.Group;
import fr.easypass.model.Password;

/**
 * Servlet implementation class PasswordServlet
 */
@WebServlet(name = "PasswordServlet", description = "Password Servlet", urlPatterns = {
        PasswordServlet.urlPrefix + "/liste", PasswordServlet.urlPrefix + "/voir",
        PasswordServlet.urlPrefix + "/editer", PasswordServlet.urlPrefix + "/creer",
        PasswordServlet.urlPrefix + "/supprimer" })
public class PasswordServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private HashMap<String, String> errors;

    public static final String urlPrefix = "/admin/mot-de-passe";
    public static final String baseURL = "/easypass" + urlPrefix;
    public static final String viewPathPrefix = "/WEB-INF/html/password";
    public final PasswordManager passwordManager = new PasswordManager();
    public final GroupManager groupManager = new GroupManager();
    public final UserManager userManager = new UserManager();
    public final CategoryManager categoryManager = new CategoryManager();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        super.doGet(request, response);

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

        Map<Integer, Password> passwords = this.passwordManager.getPasswords();

        request.setAttribute("passwords", passwords.values());

        request.getRequestDispatcher("/WEB-INF/html/password/list.jsp").forward(request, response);

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
                request.getRequestDispatcher(PasswordServlet.viewPathPrefix + "/show.jsp").forward(request, response);

                return;
            }
        }

        response.sendRedirect(PasswordServlet.baseURL);
        return;
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        String method = request.getMethod();
        
        Integer ownerId = this.checkOwnerParam(request, response);
        Boolean ownerType = BooleanUtils.toBooleanObject(request.getParameter("ownerType"));
        
        if (ownerType != Password.OWNER_TYPE_GROUP && ownerType != Password.OWNER_TYPE_USER) {
            session.setAttribute("alertClass", "alert-danger");
            session.setAttribute("alertMessage", "Cette page n'existe pas.");
            response.sendRedirect("/easypass/admin");
            return;
        }

        if (method == "POST") {

            String title = request.getParameter("title");
            String site = request.getParameter("site");
            String password = request.getParameter("password");
            Integer categoryId = NumberUtils.createInteger(request.getParameter("categoryId"));
            String informations = request.getParameter("informations");

            Password pwd = new Password(title, site, password, informations);

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

                if (ownerType == Password.OWNER_TYPE_GROUP) {
                    response.sendRedirect(GroupServlet.baseURL + "/voir?groupId=" + ownerId);
                } else {
                    response.sendRedirect(UserServlet.baseURL + "/voir?userId=" + ownerId);
                }
                
                return;

            } else {
                
                request.setAttribute("errors", errors);
            }

        }

        Map<Integer, Category> categories = this.categoryManager.getCategories();

        request.setAttribute("formAction", PasswordServlet.baseURL + "/creer");
        request.setAttribute("categories", categories.values());
        request.setAttribute("ownerId", ownerId);
        request.setAttribute("ownerType", ownerType);
        request.getRequestDispatcher(PasswordServlet.viewPathPrefix + "/create.jsp").forward(request, response);

        return;

    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        /*
         * String passwordParam = request.getParameter("password"); if
         * (passwordParam == null) {
         * response.sendRedirect(PasswordServlet.baseURL + "/liste"); return; }
         * 
         * // Simulating database request final Password password =
         * this.passwordManager.getPassword(passwordParam);
         * 
         * if (password == null) { response.sendRedirect(PasswordServlet.baseURL
         * + "/liste"); return; }
         * 
         * request.setAttribute("password", password);
         * request.getRequestDispatcher(PasswordServlet.viewPathPrefix +
         * "/edit.jsp").forward(request, response);
         */

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
            this.alertOwnerNotFound(request, response);
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

    private void alertOwnerNotFound(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("alertClass", "alert-danger");
        session.setAttribute("alertMessage", "Le propriétaire n'a pas été trouvé.");

        return;

    }

}
