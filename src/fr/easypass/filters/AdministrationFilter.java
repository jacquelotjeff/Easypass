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

import fr.easypass.model.User;
import fr.easypass.servlets.LoginServlet;
import fr.easypass.servlets.front.FrontUserServlet;

public class AdministrationFilter implements Filter {

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
        
        User user = LoginServlet.getCurrentUser(request);
        
        if (!user.getAdmin()) {
            
            session.setAttribute("alertClass", "alert-warning");
            session.setAttribute("alertMessage", "Accès interdit");
            response.sendRedirect(request.getServletContext().getContextPath() + FrontUserServlet.prefixURL);

        } else {
            chain.doFilter(request, response);
        }


    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

}
