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

import org.apache.commons.lang.math.NumberUtils;

import fr.easypass.manager.CategoryManager;
import fr.easypass.model.Category;
import fr.easypass.servlets.BaseServlet;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet(name = "BackCategoryServlet", description = "Category Servlet", urlPatterns = { BackCategoryServlet.prefixURL + "",
        BackCategoryServlet.prefixURL + "/creer", BackCategoryServlet.prefixURL + "/voir",
        BackCategoryServlet.prefixURL + "/editer", BackCategoryServlet.prefixURL + "/supprimer" })
public class BackCategoryServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;
    private HashMap<String, String> errors;

    public static final String prefixURL = "/admin/categories";
    public static final String viewPathPrefix = "/WEB-INF/html/back/category";
    public final CategoryManager categoryManager = new CategoryManager();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BackCategoryServlet() {
        super();
        this.errors = new HashMap<String, String>();
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
        } else if (uri.contains("/creer")) {
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
        doGet(request, response);
    }

    private void list(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Map<Integer, Category> categories = categoryManager.getCategories();

        request.setAttribute("categories", categories.values());

        request.getRequestDispatcher(BackCategoryServlet.viewPathPrefix + "/list.jsp").forward(request, response);

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
                request.getRequestDispatcher(BackCategoryServlet.viewPathPrefix + "/show.jsp").forward(request, response);

                return;
            }
        }

        response.sendRedirect(this.getServletContext().getContextPath() + BackCategoryServlet.prefixURL);
        return;
    }

    private void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        final String method = request.getMethod();

        if (method == "GET") {

            request.setAttribute("formAction", "creer");
            request.getRequestDispatcher(BackCategoryServlet.viewPathPrefix + "/create.jsp").forward(request, response);

            return;

        } else {

            String name = request.getParameter("name");
            String logo = request.getParameter("logo");

            Category ctg = new Category(name, logo);

            errors = ctg.isValid();

            if (errors.isEmpty()) {

                final Integer success = this.categoryManager.insertCategory(name, logo);

                if (success == 1) {

                    session.setAttribute("alertClass", "alert-success");
                    session.setAttribute("alertMessage", "La catégorie à bien été créée");

                } else {

                    session.setAttribute("alertClass", "alert-danger");
                    session.setAttribute("alertMessage", "Le catégorie n'a pas pu être créée");
                }
            } else {
                request.setAttribute("errors", errors);
                request.getRequestDispatcher(BackCategoryServlet.viewPathPrefix + "/create.jsp").forward(request, response);
            }

        }

        response.sendRedirect(this.getServletContext().getContextPath() + BackCategoryServlet.prefixURL);
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

                request.getRequestDispatcher(BackCategoryServlet.viewPathPrefix + "/edit.jsp").forward(request, response);

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

        response.sendRedirect(this.getServletContext().getContextPath() + BackCategoryServlet.prefixURL);
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

        response.sendRedirect(this.getServletContext().getContextPath() + BackCategoryServlet.prefixURL);

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
