<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="user-image text-center">
    <img src="${pageContext.servletContext.contextPath}/fichier?nom=User.png" alt="Utilisateur" title="Utilisateur" class="img-circle" width="100">
</div>
<div class="user-info-block">
    <div class="user-heading">
        <div class="col-sm-12">
            <div>
                <h3>${user.getUsername()}</h3>
                <span class="help-block">${user.getEmail()}</span>
            </div>
        </div>
    </div>
    
    <%
        List<Map<String, String>> menu = new ArrayList<Map<String,String>>();
        
        Map<String, String> itemUser = new HashMap<>();
        itemUser.put("href", pageContext.getServletContext().getContextPath() + "/utilisateur");
        itemUser.put("id", "btn-user");
        itemUser.put("iclass", "fa fa-users");
        itemUser.put("label", "Utilisateur");
        menu.add(itemUser);
        
        Map<String, String> itemPasswords = new HashMap<>();
        itemPasswords.put("href", pageContext.getServletContext().getContextPath() + "/utilisateur/mots-de-passes");
        itemPasswords.put("id", "btn-passwords");
        itemPasswords.put("iclass", "fa fa-key");
        itemPasswords.put("label", "Mots de passes");
        menu.add(itemPasswords);
        
        Map<String, String> itemGroupes = new HashMap<>();
        itemGroupes.put("href", pageContext.getServletContext().getContextPath() + "/utilisateur/groupes");
        itemGroupes.put("id", "btn-groups");
        itemGroupes.put("iclass", "fa fa-users");
        itemGroupes.put("label", "Groupes");
        menu.add(itemGroupes);
        
        request.setAttribute("menu", menu);
    %>
    
    <ul class="navigation">
        <c:forEach items="${menu}" var="item">
            <c:set var="active" value="${requestURL == item.href ? 'active' : '' }" />
            <li class="${active}">
                <a id="${item.id}" href="${item.href}">
                    <i class="${item.iclass}"></i>
                </a>
            </li>
        </c:forEach>
    </ul>
</div>
