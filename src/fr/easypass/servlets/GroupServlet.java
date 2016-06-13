package fr.easypass.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;

import fr.easypass.manager.GroupManager;
import fr.easypass.manager.UserManager;
import fr.easypass.model.Group;
import fr.easypass.model.User;

/**
 * Servlet implementation class GroupServlet
 */
@WebServlet(name = "GroupServlet", description = "Group Servlet", urlPatterns = { GroupServlet.urlPrefix + "",
        GroupServlet.urlPrefix + "/voir", GroupServlet.urlPrefix + "/editer", GroupServlet.urlPrefix + "/creer", GroupServlet.urlPrefix + "/supprimer",
        GroupServlet.urlPrefix + "/ajouter-utilisateur", GroupServlet.urlPrefix + "/supprimer-utilisateur", GroupServlet.urlPrefix + "/admin-utilisateur"})
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
        } else if (uri.contains(urlPrefix + "/ajouter-utilisateur")) {
            this.addUser(request, response);
        } else if (uri.contains(urlPrefix + "/supprimer-utilisateur")) {
            this.deleteUser(request, response);
        } else if (uri.contains(urlPrefix + "/admin-utilisateur")) {
            this.adminUser(request, response);
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
    	
    	HttpSession session = request.getSession();
    	
    	try {
			
    		Integer groupId = NumberUtils.createInteger(request.getParameter("groupId"));
    		
    		final Group group = this.groupManager.getGroup(groupId);
    		
    		if (group == null) {
    			
    			session.setAttribute("alertClass", "alert-danger");
    			session.setAttribute("alertMessage", "Le groupe n'existe pas");
    			response.sendRedirect(GroupServlet.baseURL);
    			return;
    		}
    		
    		request.setAttribute("group", group);
    		request.getRequestDispatcher(GroupServlet.viewPathPrefix + "/show.jsp").forward(request, response);
    		
    		return;
    		
		} catch (Exception e) {
			
			session.setAttribute("alertClass", "alert-danger");
			session.setAttribute("alertMessage", "Impossible de récupérer le groupe.");
			response.sendRedirect(GroupServlet.baseURL);
			return;
			
		}
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
    	HttpSession session = request.getSession();
    	final String method = request.getMethod();
    	
    	try {
			
			if (method == "GET") {
				
				final Map<Integer, User> users = userManager.getUsers();
				request.setAttribute("users", users.values());
				request.setAttribute("formAction", "creer");
				request.getRequestDispatcher(GroupServlet.viewPathPrefix + "/create.jsp").forward(request, response);
				
				return;
				
			} else {
				
				String name = request.getParameter("name");
				String description = request.getParameter("description");
				String logo = request.getParameter("logo");
				String[] users = request.getParameterValues("users");
				
				String[] admins = {};
				//String[] admins = request.getParameterValues("admins");
				
				final Integer success = this.groupManager.insertGroup(name, description, logo, users, admins);
				
				if (success == 1) {
					
					session.setAttribute("alertClass", "alert-success");
					session.setAttribute("alertMessage", "Le groupe à bien été créé");
					
				} else {
					
					session.setAttribute("alertClass", "alert-danger");
					session.setAttribute("alertMessage", "Le groupe n'a pas pu être créé");
				}
				
			}
			
		} catch (Exception e) {
			
			session.setAttribute("alertClass", "alert-danger");
			session.setAttribute("alertMessage", "Le groupe n'a pas pu être créé");
			
		}
    	
    	response.sendRedirect(GroupServlet.baseURL);
    	return;

    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    	HttpSession session = request.getSession();
		final String method = request.getMethod();

		if (method == "GET") {
			
			try {
				
				Integer groupId = NumberUtils.createInteger(request.getParameter("groupId")); 
				final Group group = this.groupManager.getGroup(groupId);
				
				if (group == null) {
					response.sendRedirect(GroupServlet.baseURL);
					return;
				}
				
				final Map<Integer, User> availableUsers = userManager.getUsersAvailableByGroup(groupId);
				final Map<Integer, User> groupUsers =  userManager.getUsersByGroup(groupId).get("groupUsers");
				final Map<Integer, User> groupAdmins = userManager.getUsersByGroup(groupId).get("groupAdmins");
				
				request.setAttribute("users", availableUsers.values());
				request.setAttribute("groupUsers", groupUsers.values());
				request.setAttribute("groupAdmins", groupAdmins);
				
				request.setAttribute("group", group);
				request.setAttribute("formAction", "editer");
				
				request.getRequestDispatcher(GroupServlet.viewPathPrefix + "/edit.jsp").forward(request, response);
				return;
				
			} catch (Exception e) {
				
				System.out.print(e.getStackTrace());
				session.setAttribute("alertClass", "alert-danger");
				session.setAttribute("alertMessage", "Le groupe n'a pas été trouvé.");
			}

		} else {
			
			try {
				
				Integer groupId = NumberUtils.createInteger(request.getParameter("group"));
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
				
			} catch (Exception e) {
				
				session.setAttribute("alertClass", "alert-danger");
				session.setAttribute("alertMessage", "Le groupe n'a pas pu être édité.");
			}
		}
		
		response.sendRedirect(GroupServlet.baseURL);
		return;

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
    	HttpSession session = request.getSession();
		final String method = request.getMethod();

		try {
			
			if (method == "POST") {
				Integer groupId = NumberUtils.createInteger(request.getParameter("groupId"));
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
			
		} catch (Exception e) {
			session.setAttribute("alertClass", "alert-danger");
			session.setAttribute("alertMessage", "Le groupe n'a pas pu être supprimé.");
		}
		
		response.sendRedirect(GroupServlet.baseURL);

        return;
    }
    
    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
    	HttpSession session = request.getSession();
		final String method = request.getMethod();
		
		try {
			
			if (method == "POST") {
				
				Integer groupId = NumberUtils.createInteger(request.getParameter("groupId"));
				Integer userId = NumberUtils.createInteger(request.getParameter("userId"));
					
				final Integer success = this.groupManager.addUser(groupId, userId);
				
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
			
			
		} catch (Exception e) {
			
			session.setAttribute("alertClass", "alert-danger");
			session.setAttribute("alertMessage", "Impossible d'ajouter l'utilisateur.");
			
		}
		
		response.sendRedirect(GroupServlet.baseURL);

        return;
        
    }
    
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
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
		
		response.sendRedirect(GroupServlet.baseURL);

        return;
        
    }
    
    private void adminUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
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
				}
				
			} else {
				session.setAttribute("alertClass", "alert-danger");
				session.setAttribute("alertMessage", "Accès interdit");
			}
			
		} catch (Exception e) {
			
			session.setAttribute("alertClass", "alert-danger");
			session.setAttribute("alertMessage", "Impossible de changer le statut de l'utilisateur.");
			
		}
		
		response.sendRedirect(GroupServlet.baseURL);

        return;
    	
    }

}
