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

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;

import fr.easypass.manager.CategoryManager;
import fr.easypass.manager.GroupManager;
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
@WebServlet(name = "FrontGroupServlet", description = "Front Group Servlet", urlPatterns = { FrontGroupServlet.prefixURL + "",
        FrontGroupServlet.prefixURL + "/voir", FrontGroupServlet.prefixURL + "/editer", FrontGroupServlet.prefixURL + "/creer",
        FrontGroupServlet.prefixURL + "/supprimer", FrontGroupServlet.prefixURL + "/ajouter-utilisateur",
        FrontGroupServlet.prefixURL + "/supprimer-utilisateur", FrontGroupServlet.prefixURL + "/admin-utilisateur" })
public class FrontGroupServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;
    public static final String prefixURL = "/utilisateur/groupes";
    public static final String viewPathPrefix = "/WEB-INF/html/front/group";
    public static String rootPath;
    public static String baseURL;
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
        FrontGroupServlet.rootPath = this.getServletContext().getContextPath();
        FrontGroupServlet.baseURL = FrontGroupServlet.rootPath + FrontGroupServlet.prefixURL;
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
        } else if (uri.contains(prefixURL + "/ajouter-utilisateur")) {
            this.addUser(request, response);
        } else if (uri.contains(prefixURL + "/supprimer-utilisateur")) {
            this.deleteUser(request, response);
        } else if (uri.contains(prefixURL + "/admin-utilisateur")) {
            this.adminUser(request, response);
        } else if (uri.contains(prefixURL + "/creer")) {
            this.create(request, response);
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

            if (group == null) {
                this.alertGroupNotFound(request, response);
            } else {

                request.setAttribute("group", group);
                request.getRequestDispatcher(FrontGroupServlet.viewPathPrefix + "/show.jsp").forward(request, response);

                return;
            }
        }

        response.sendRedirect(FrontGroupServlet.baseURL);
        return;
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        final String method = request.getMethod();

        if (method == "POST") {

            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String logo = request.getParameter("logo");
            String[] users = request.getParameterValues("users");
            String[] admins = {};
            
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
                
                response.sendRedirect(FrontUserServlet.baseURL);
                return;
                
            } else {
                
                request.setAttribute("errors", errors);
            }
            
        }
        
        final Map<Integer, User> users = userManager.getUsers();
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
                    this.alertGroupNotFound(request, response);
                }

                final Map<Integer, User> availableUsers = userManager.getUsersAvailableByGroup(groupId);
                final Map<Integer, User> groupUsers = userManager.getUsersByGroup(groupId).get("groupUsers");
                final Map<Integer, User> groupAdmins = userManager.getUsersByGroup(groupId).get("groupAdmins");

                request.setAttribute("users", availableUsers.values());
                request.setAttribute("groupUsers", groupUsers.values());
                request.setAttribute("groupAdmins", groupAdmins);
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

        response.sendRedirect(FrontGroupServlet.baseURL);
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

        response.sendRedirect(FrontGroupServlet.baseURL);

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

                    response.sendRedirect(FrontGroupServlet.baseURL + "/editer" + "?groupId=" + groupId);

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

        response.sendRedirect(FrontGroupServlet.baseURL);

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

                    response.sendRedirect(FrontGroupServlet.baseURL + "/editer" + "?groupId=" + groupId);

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

        response.sendRedirect(FrontGroupServlet.baseURL);

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

                    response.sendRedirect(FrontGroupServlet.baseURL + "/editer" + "?groupId=" + groupId);

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
            this.alertGroupNotFound(request, response);
        }

        return groupId;
    }

    private void alertGroupNotFound(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("alertClass", "alert-danger");
        session.setAttribute("alertMessage", "Le groupe n'a pas été trouvé.");

        return;

    }

}