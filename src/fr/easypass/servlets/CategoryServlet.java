package fr.easypass.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;

import fr.easypass.manager.CategoryManager;
import fr.easypass.model.Category;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet(name = "CategoryServlet", description = "Category Servlet", urlPatterns = { CategoryServlet.urlPrefix + "",
        CategoryServlet.urlPrefix + "/creer", CategoryServlet.urlPrefix + "/voir",
        CategoryServlet.urlPrefix + "/editer", CategoryServlet.urlPrefix + "/supprimer" })
public class CategoryServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;
    public static final String urlPrefix = "/admin/categories";
    public static final String baseURL = "/easypass" + urlPrefix;
    public static final String viewPathPrefix = "/WEB-INF/html/category";
    public final CategoryManager categoryManager = new CategoryManager();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        super.doGet(request, response);
        
        final String uri = request.getRequestURI();

        if (uri.contains(urlPrefix + "/voir")) {
            this.show(request, response);
        } else if (uri.contains("/creer")) {
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

        Map<Integer, Category> categories = categoryManager.getCategories();

        request.setAttribute("categories", categories.values());

        request.getRequestDispatcher(CategoryServlet.viewPathPrefix + "/list.jsp").forward(request, response);

        return;
    }

    private void show(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Integer categoryId = this.checkCategoryParam(request, response);

        if (categoryId > 0) {

            final Category category = this.categoryManager.getCategory(categoryId);

            if (category == null) {
                this.alertCategoryNotFound(request, response);
            } else {

                request.setAttribute("category", category);
                request.getRequestDispatcher(CategoryServlet.viewPathPrefix + "/show.jsp").forward(request, response);

                return;
            }
        }

        response.sendRedirect(CategoryServlet.baseURL);
        return;
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        final String method = request.getMethod();

        if (method == "GET") {

            request.setAttribute("formAction", "creer");
            request.getRequestDispatcher(CategoryServlet.viewPathPrefix + "/create.jsp").forward(request, response);

            return;

        } else {

            String name = request.getParameter("name");
            String logo = request.getParameter("logo");
            
            System.out.println(name);

            final Integer success = this.categoryManager.insertCategory(name, logo);

            if (success == 1) {

                session.setAttribute("alertClass", "alert-success");
                session.setAttribute("alertMessage", "La catégorie à bien été créée");

            } else {

                session.setAttribute("alertClass", "alert-danger");
                session.setAttribute("alertMessage", "Le catégorie n'a pas pu être créée");
            }

        }

        response.sendRedirect(CategoryServlet.baseURL);
        return;

    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        final String method = request.getMethod();

        Integer categoryId = this.checkCategoryParam(request, response);
        if (categoryId > 0) {

            if (method == "GET") {

                final Category category = this.categoryManager.getCategory(categoryId);

                if (category == null) {
                    this.alertCategoryNotFound(request, response);
                }

                request.setAttribute("category", category);
                request.setAttribute("formAction", "editer");

                request.getRequestDispatcher(CategoryServlet.viewPathPrefix + "/edit.jsp").forward(request, response);

                return;

            } else {

                String name = request.getParameter("name");
                String logo = request.getParameter("logo");

                final Integer success = this.categoryManager.editCategory(categoryId, name, logo);

                if (success == 1) {
                    session.setAttribute("alertClass", "alert-success");
                    session.setAttribute("alertMessage", "La catégorie a bien été éditée.");

                } else {
                    session.setAttribute("alertClass", "alert-danger");
                    session.setAttribute("alertMessage", "Le catégorie n'a pas pu être éditée.");
                }

            }

        }

        response.sendRedirect(CategoryServlet.baseURL);
        return;

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        final String method = request.getMethod();

        Integer categoryId = this.checkCategoryParam(request, response);

        if (categoryId > 0) {

            if (method == "POST") {
                final Integer success = this.categoryManager.deleteCategory(categoryId);

                if (success == 0) {
                    session.setAttribute("alertClass", "alert-danger");
                    session.setAttribute("alertMessage", "La catégorie n'a pas pu être supprimée.");
                } else {
                    session.setAttribute("alertClass", "alert-success");
                    session.setAttribute("alertMessage", "La catégorie a bien été supprimée.");
                }

            } else {
                session.setAttribute("alertClass", "alert-danger");
                session.setAttribute("alertMessage", "Accès interdit");
            }
        }

        response.sendRedirect(CategoryServlet.baseURL);

        return;
    }

    private Integer checkCategoryParam(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Integer categoryId = 0;

        try {
            categoryId = NumberUtils.createInteger(request.getParameter("categoryId"));
        } catch (Exception e) {
            this.alertCategoryNotFound(request, response);
        }

        return categoryId;
    }

    private void alertCategoryNotFound(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("alertClass", "alert-danger");
        session.setAttribute("alertMessage", "La catégorie n'a pas été trouvé.");

        return;

    }

}
