package fr.easypass.filters;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;

import fr.easypass.manager.GroupManager;
import fr.easypass.manager.PasswordManager;
import fr.easypass.manager.UserManager;
import fr.easypass.model.Group;
import fr.easypass.model.Password;
import fr.easypass.model.User;
import fr.easypass.servlets.LoginServlet;
import fr.easypass.servlets.front.FrontUserServlet;

public class PasswordOwnerFilter implements Filter {

    @Override
    public void destroy() {
        // Nothing to do
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        User user = LoginServlet.getCurrentUser(request);
        PasswordManager passwordManager = new PasswordManager();
        UserManager userManager = new UserManager();

        if (NumberUtils.isNumber(request.getParameter("passwordId"))) {

            Integer passwordId = NumberUtils.createInteger(request.getParameter("passwordId"));
            Password password = passwordManager.getPassword(passwordId);

            if (password.getOwnerGroup() != null) {

                GroupManager groupManager = new GroupManager();
                Group group = groupManager.getGroup(password.getOwnerGroup());
                Map<Integer, User> usersAdmin = userManager.getUsersByGroup(group.getId()).get("adminsGroup");

                if (usersAdmin.containsKey(user.getId())) {
                    
                    chain.doFilter(request, response);

                    return;
                }

            } else {

                Map<Integer, Password> passwordsUser = passwordManager.getPasswordsByUser(user.getId());

                if (passwordsUser.containsKey(passwordId)) {
                    
                    chain.doFilter(request, response);
                    
                    return;
                }

            }

        }

        this.restrict(session, response);

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // Nothing to do
    }

    private void restrict(HttpSession session, HttpServletResponse response) throws IOException {

        session.setAttribute("alertClass", "alert-warning");
        session.setAttribute("alertMessage", "Accès interdit.");
        response.sendRedirect(session.getServletContext().getContextPath() + FrontUserServlet.URL_BASE);

    }

}
