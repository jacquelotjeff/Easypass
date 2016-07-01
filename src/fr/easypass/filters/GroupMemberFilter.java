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
import fr.easypass.model.Group;
import fr.easypass.model.User;
import fr.easypass.servlets.LoginServlet;
import fr.easypass.servlets.front.FrontUserServlet;

public class GroupMemberFilter implements Filter {

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
        GroupManager groupManager = new GroupManager();
        
        try {
            
            Integer groupId = NumberUtils.createInteger(request.getParameter("groupId"));
            
            Map<Integer, Group> groups = groupManager.getGroupByUsers(user.getId()).get("groups");
            
            if (groups.containsKey(groupId)) {
                
                chain.doFilter(request, response);
                
            } else {
                
                this.restrict(session, response);
                
            }
            
        } catch (Exception e) {
            
            e.printStackTrace();
            this.restrict(session, response);
            
        }

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }
    
    private void restrict(HttpSession session, HttpServletResponse response) throws IOException{
        
        session.setAttribute("alertClass", "alert-warning");
        session.setAttribute("alertMessage", "Accès interdit.");
        response.sendRedirect(session.getServletContext().getContextPath() + FrontUserServlet.prefixURL);
        
    }

}
