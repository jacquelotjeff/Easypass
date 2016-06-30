package fr.easypass.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthentificationFilter implements Filter {

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null) {

            session.setAttribute("alertClass", "alert-warning");
            session.setAttribute("alertMessage", "Veuillez vous identifier.");
            response.sendRedirect(request.getContextPath());

        } else {

            chain.doFilter(request, response);

        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        // TODO Auto-generated method stub

    }

}
