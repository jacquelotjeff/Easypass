package fr.easypass.servlets.front;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import fr.easypass.servlets.LoginServlet;

/**
 * Servlet implementation class GroupServlet
 */
@WebServlet(name = "FrontGroupServlet", description = "Front Group Servlet", urlPatterns = { FrontGroupServlet.URL_BASE + "",
        FrontGroupServlet.URL_BASE + "/voir", FrontGroupServlet.URL_BASE + "/editer", FrontGroupServlet.URL_BASE + "/creer",
        FrontGroupServlet.URL_BASE + "/supprimer", FrontGroupServlet.URL_BASE + "/ajouter-utilisateur",
        FrontGroupServlet.URL_BASE + "/supprimer-utilisateur", FrontGroupServlet.URL_BASE + "/admin-utilisateur" })
public class FrontGroupServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;
    public static final String URL_BASE = "/utilisateur/groupes";
    public static final String viewPathPrefix = "/WEB-INF/html/front/group";
    public final PasswordManager passwordManager = new PasswordManager();
    public final GroupManager groupManager = new GroupManager();
    public final UserManager userManager = new UserManager();
    public final CategoryManager categoryManager = new CategoryManager();
    
    
    
    private HashMap<String, String> errors;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontGroupServlet() {
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

        if (uri.contains(URL_BASE + "/voir")) {
            this.show(request, response);
        } else if (uri.contains(URL_BASE + "/ajouter-utilisateur")) {
            this.addUser(request, response);
        } else if (uri.contains(URL_BASE + "/supprimer-utilisateur")) {
            this.deleteUser(request, response);
        } else if (uri.contains(URL_BASE + "/admin-utilisateur")) {
            this.adminUser(request, response);
        } else if (uri.contains(URL_BASE + "/creer")) {
            this.create(request, response);
        } else if (uri.contains(URL_BASE + "/editer")) {
            this.edit(request, response);
        } else if (uri.contains(URL_BASE + "/supprimer")) {
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

        Map<String, Map<Integer, Group>> groups = this.groupManager.getGroupByUsers(userId);

        request.setAttribute("groups", groups.get("groups").values());
        request.setAttribute("groupsAdmin", groups.get("groupsAdmin"));
        request.setAttribute("user", user);
        request.getRequestDispatcher(FrontGroupServlet.viewPathPrefix + "/list.jsp").forward(request, response);

        return;
    }

    private void show(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Integer groupId = this.checkGroupParam(request, response);

        if (groupId > 0) {

            final Group group = this.groupManager.getGroup(groupId);
            final Map<String, Map<Integer, User>> users = userManager.getUsersByGroup(groupId);
            final Map<Integer, User> groupUsers = users.get("groupUsers");
            final Map<Integer, User> groupsAdmins = users.get("groupAdmins");
            final Map<Integer, Password> groupPasswords = passwordManager.getPasswordsByGroup(groupId);

            if (group == null) {
                this.alertGroupNotFound(request);
            } else {

                request.setAttribute("group", group);
                request.setAttribute("users", groupUsers);
                request.setAttribute("groupsAdmins", groupsAdmins);
                request.setAttribute("passwords", groupPasswords);
                request.getRequestDispatcher(FrontGroupServlet.viewPathPrefix + "/show.jsp").forward(request, response);

                return;
            }
        }

        response.sendRedirect(this.getServletContext().getContextPath() + FrontGroupServlet.URL_BASE);
        return;
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        final String method = request.getMethod();
        final User user = LoginServlet.getCurrentUser(request);

        if (method == "POST") {

            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String logo = request.getParameter("logo");
            List<String> users = new ArrayList<>(Arrays.asList(request.getParameterValues("users")));
            List<String> admins = new ArrayList<>();
            
            users.add(user.getId().toString());
            admins.add(user.getId().toString());
            
            Group group = new Group(name, description, logo);
            
            errors = group.isValid();
            
            if (errors.isEmpty()) {
                
                final Integer success = this.groupManager.insertGroup(name, description, logo, users, admins);

                if (success == 1) {

                    session.setAttribute("alertClass", "alert-success");
                    session.setAttribute("alertMessage", "Le groupe à bien été créé");

                } else {

                    session.setAttribute("alertClass", "alert-danger");
                    session.setAttribute("alertMessage", "Le groupe n'a pas pu être créé");
                }
                
                response.sendRedirect(this.getServletContext().getContextPath() + FrontGroupServlet.URL_BASE);
                return;
                
            } else {
                
                request.setAttribute("errors", errors);
            }
            
        }
        
        final Map<Integer, User> users = userManager.getUsers();
        users.remove(user.getId());
        request.setAttribute("users", users.values());
        request.setAttribute("formAction", "creer");
        request.getRequestDispatcher(FrontGroupServlet.viewPathPrefix + "/create.jsp").forward(request, response);

        return;

    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        final String method = request.getMethod();

        Integer groupId = this.checkGroupParam(request, response);
        if (groupId > 0) {

            if (method == "GET") {

                final Group group = this.groupManager.getGroup(groupId);

                if (group == null) {
                    this.alertGroupNotFound(request);
                }

                final Map<Integer, User> availableUsers = userManager.getUsersAvailableByGroup(groupId);
                final Map<String, Map<Integer, User>> users = userManager.getUsersByGroup(groupId);
                final Map<Integer, User> groupUsers = users.get("groupUsers");
                final Map<Integer, User> groupAdmins = users.get("groupAdmins");
                final Map<Integer, Password> groupPasswords = passwordManager.getPasswordsByGroup(groupId);
                final Map<Integer, Category> categories = categoryManager.getCategories();

                request.setAttribute("users", availableUsers);
                request.setAttribute("groupUsers", groupUsers);
                request.setAttribute("groupAdmins", groupAdmins);
                request.setAttribute("groupPasswords", groupPasswords);
                request.setAttribute("categories", categories);
                
                request.setAttribute("group", group);
                request.setAttribute("formAction", "editer");

                request.getRequestDispatcher(FrontGroupServlet.viewPathPrefix + "/edit.jsp").forward(request, response);

                return;

            } else {

                String name = request.getParameter("name");
                String description = request.getParameter("description");
                String logo = request.getParameter("logo");

                final Integer success = this.groupManager.editGroup(groupId, name, description, logo);

                if (success == 1) {
                    session.setAttribute("alertClass", "alert-success");
                    session.setAttribute("alertMessage", "Le groupe a bien été édité.");

                } else {
                    session.setAttribute("alertClass", "alert-danger");
                    session.setAttribute("alertMessage", "Le groupe n'a pas pu être édité.");
                }

            }

        }

        response.sendRedirect(this.getServletContext().getContextPath() + FrontGroupServlet.URL_BASE);
        return;

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        final String method = request.getMethod();

        Integer groupId = this.checkGroupParam(request, response);

        if (groupId > 0) {

            if (method == "POST") {
                final Integer success = this.groupManager.deleteGroup(groupId);

                if (success == 0) {
                    session.setAttribute("alertClass", "alert-danger");
                    session.setAttribute("alertMessage", "Le groupe n'a pas pu être supprimé.");
                } else {
                    session.setAttribute("alertClass", "alert-success");
                    session.setAttribute("alertMessage", "Le groupe a bien été supprimé.");
                }

            } else {
                session.setAttribute("alertClass", "alert-danger");
                session.setAttribute("alertMessage", "Accès interdit");
            }
        }

        response.sendRedirect(this.getServletContext().getContextPath() + FrontGroupServlet.URL_BASE);

        return;
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        final String method = request.getMethod();

        try {

            if (method == "POST") {

                Integer groupId = NumberUtils.createInteger(request.getParameter("groupId"));
                Integer userId = NumberUtils.createInteger(request.getParameter("userId"));

                final Integer success = this.groupManager.addUser(groupId, userId);

                if (success == 0) {
                    session.setAttribute("alertClass", "alert-danger");
                    session.setAttribute("alertMessage", "L'utilisateur  n'a pas pu être ajouté au groupe.");
                } else {
                    session.setAttribute("alertClass", "alert-success");
                    session.setAttribute("alertMessage", "L'utilisateur a été ajouté au groupe.");

                    response.sendRedirect(this.getServletContext().getContextPath() + FrontGroupServlet.URL_BASE + "/editer" + "?groupId=" + groupId);

                    return;
                }

            } else {
                session.setAttribute("alertClass", "alert-danger");
                session.setAttribute("alertMessage", "Accès interdit");
            }

        } catch (Exception e) {

            session.setAttribute("alertClass", "alert-danger");
            session.setAttribute("alertMessage", "Impossible d'ajouter l'utilisateur.");

        }

        response.sendRedirect(this.getServletContext().getContextPath() + FrontGroupServlet.URL_BASE);

        return;

    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        final String method = request.getMethod();

        try {

            Integer groupId = NumberUtils.createInteger(request.getParameter("groupId"));
            Integer userId = NumberUtils.createInteger(request.getParameter("userId"));

            if (method == "POST") {

                final Integer success = this.groupManager.deleteUser(groupId, userId);

                if (success == 0) {
                    session.setAttribute("alertClass", "alert-danger");
                    session.setAttribute("alertMessage", "L'utilisateur n'a pas pu être retiré du groupe");
                } else {
                    session.setAttribute("alertClass", "alert-success");
                    session.setAttribute("alertMessage", "L'utilisateur a été retiré du groupe.");

                    response.sendRedirect(this.getServletContext().getContextPath() + FrontGroupServlet.URL_BASE + "/editer" + "?groupId=" + groupId);

                    return;

                }

            } else {
                session.setAttribute("alertClass", "alert-danger");
                session.setAttribute("alertMessage", "Accès interdit");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("alertClass", "alert-danger");
            session.setAttribute("alertMessage", "Impossible d'ajouter l'utilisateur.");

        }

        response.sendRedirect(this.getServletContext().getContextPath() + FrontGroupServlet.URL_BASE);

        return;

    }

    private void adminUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        final String method = request.getMethod();

        try {

            if (method == "POST") {

                Integer groupId = NumberUtils.createInteger(request.getParameter("groupId"));
                Integer userId = NumberUtils.createInteger(request.getParameter("userId"));
                Boolean admin = BooleanUtils.toBoolean(request.getParameter("admin"));

                final Integer success = this.groupManager.setUserAdmin(groupId, userId, admin);

                if (success == 0) {
                    session.setAttribute("alertClass", "alert-danger");
                    session.setAttribute("alertMessage", "Le statut de l'utilisateur n'a pas été mis à jour.");
                } else {
                    session.setAttribute("alertClass", "alert-success");
                    session.setAttribute("alertMessage", "Le statut de l'utilisateur a été mis à jour.");

                    response.sendRedirect(this.getServletContext().getContextPath() + FrontGroupServlet.URL_BASE + "/editer" + "?groupId=" + groupId);

                    return;
                }

            } else {
                session.setAttribute("alertClass", "alert-danger");
                session.setAttribute("alertMessage", "Accès interdit");
            }

        } catch (Exception e) {

            session.setAttribute("alertClass", "alert-danger");
            session.setAttribute("alertMessage", "Impossible de changer le statut de l'utilisateur.");

        }

        return;

    }

    private Integer checkGroupParam(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Integer groupId = 0;

        try {
            groupId = NumberUtils.createInteger(request.getParameter("groupId"));
        } catch (Exception e) {
            this.alertGroupNotFound(request);
        }

        return groupId;
    }

    private void alertGroupNotFound(HttpServletRequest request) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("alertClass", "alert-danger");
        session.setAttribute("alertMessage", "Le groupe n'a pas été trouvé.");

        return;

    }

}
