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

import fr.easypass.manager.GroupManager;
import fr.easypass.manager.UserManager;
import fr.easypass.model.Group;
import fr.easypass.model.User;

/**
 * Servlet implementation class GroupServlet
 */
@WebServlet(name = "GroupServlet", description = "Group Servlet", urlPatterns = { GroupServlet.urlPrefix + "",
        GroupServlet.urlPrefix + "/voir", GroupServlet.urlPrefix + "/editer", GroupServlet.urlPrefix + "/creer", GroupServlet.urlPrefix + "/supprimer" })
public class GroupServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public static final String urlPrefix = "/admin/groupes";
    public static final String baseURL = "/easypass" + urlPrefix;
    public static final String viewPathPrefix = "/WEB-INF/html/group";
    public final GroupManager groupManager = new GroupManager();
    public final UserManager userManager = new UserManager();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final String uri = request.getRequestURI();

        if (uri.contains(urlPrefix + "/voir")) {
            this.show(request, response);
        } else if (uri.contains(urlPrefix + "/creer")) {
            this.create(request, response);
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

    private void list(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Map<Integer, Group> groups = groupManager.getGroups();

        request.setAttribute("groups", groups.values());

        request.getRequestDispatcher("/WEB-INF/html/group/list.jsp").forward(request, response);

        return;
    }

    private void show(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
		//Getting the HTTPSession to pass Flash Message
		HttpSession session = request.getSession();
		
		final Group group = this.groupManager.getGroup(request);

		if (group == null) {
			
			session.setAttribute("alertClass", "alert-danger");
			session.setAttribute("alertMessage", "Le groupe n'existe pas");
			response.sendRedirect(GroupServlet.baseURL);
			return;
		}

		request.setAttribute("group", group);
		request.getRequestDispatcher(GroupServlet.viewPathPrefix + "/show.jsp").forward(request, response);
        
		return;
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
    	HttpSession session = request.getSession();
    	final String method = request.getMethod();

		if (method == "GET") {
			
			final HashMap<Integer, User> users = userManager.getUsers();
			request.setAttribute("users", users.values());
			request.setAttribute("formAction", "creer");
			request.getRequestDispatcher(GroupServlet.viewPathPrefix + "/create.jsp").forward(request, response);
			
		} else {
			
			final Integer success = this.groupManager.insertGroup(request);
			
			if (success == 1) {
				
				session.setAttribute("alertClass", "alert-success");
				session.setAttribute("alertMessage", "Le groupe à bien été créé");
					
			} else {
				
				session.setAttribute("alertClass", "alert-danger");
				session.setAttribute("alertMessage", "Le groupe n'a pas pu être créé");
			}
			
			response.sendRedirect(GroupServlet.baseURL);
		}
		
        return;
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    	HttpSession session = request.getSession();
		final String method = request.getMethod();

		if (method == "GET") {
			
			final Group group = this.groupManager.getGroup(request);

			if (group == null) {
				response.sendRedirect(GroupServlet.baseURL);
				return;
			}
			
			final HashMap<Integer, User> users = userManager.getUsers();
			request.setAttribute("users", users.values());
			request.setAttribute("group", group);
			request.setAttribute("formAction", "editer");
			request.getRequestDispatcher(GroupServlet.viewPathPrefix + "/edit.jsp").forward(request, response);

		} else {

			final Integer success = this.groupManager.editGroup(request);

			if (success == 1) {
				session.setAttribute("alertClass", "alert-success");
				session.setAttribute("alertMessage", "Le groupe a bien été édité.");
				response.sendRedirect(GroupServlet.baseURL);
			} else {
				session.setAttribute("alertClass", "alert-danger");
				session.setAttribute("alertMessage", "Le groupe n'a pas pu être édité.");
				response.sendRedirect(GroupServlet.baseURL);
			}

		}

		return;

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
    	HttpSession session = request.getSession();
		final String method = request.getMethod();

		if (method == "POST") {
			
			final Integer success = this.groupManager.deleteGroup(request);
			
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
		
		response.sendRedirect(GroupServlet.baseURL);

        return;
    }

}
