package fr.easypass.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.easypass.manager.GroupManager;
import fr.easypass.model.Group;

/**
 * Servlet implementation class GroupServlet
 */
@WebServlet(name = "GroupServlet", description = "Group Servlet", urlPatterns = { GroupServlet.urlPrefix + "",
        GroupServlet.urlPrefix + "/voir", GroupServlet.urlPrefix + "/editer", GroupServlet.urlPrefix + "/creer", GroupServlet.urlPrefix + "/supprimer" })
public class GroupServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public static final String urlPrefix = "/admin/groupes";
    public static final String baseURL = "/easypass" + urlPrefix;
    public static final String viewPathPrefix = "/WEB-INF/html/user";
    public final GroupManager groupManager = new GroupManager();

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

        HashMap<String, Group> groups = groupManager.getGroups();

        request.setAttribute("groups", groups.values());

        request.getRequestDispatcher("/WEB-INF/html/group/list.jsp").forward(request, response);

        return;
    }

    private void show(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return;
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return;
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return;
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // request.getRequestDispatcher("/WEB-INF/html/user/list.jsp").forward(request,
        // response);

        return;
    }

}
