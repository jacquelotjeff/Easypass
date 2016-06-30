<%@ page import="fr.easypass.servlets.BaseServlet" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="user-image text-center">
    <img src="http://bootdey.com/img/Content/User_for_snippets.png" alt="Utilisateur" title="Utilisateur" class="img-circle" width="100">
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
    
    <jsp:useBean id="menu" class="java.util.HashMap" scope="request"/>
    <c:set target="${menu}" property="${BaseServlet.rootPath}/utilisateur" value="fa fa-user"/> 
    <c:set target="${menu}" property="${BaseServlet.rootPath}/utilisateur/mots-de-passes" value="fa fa-key"/>
    <c:set target="${menu}" property="${BaseServlet.rootPath}/utilisateur/groupes" value="fa fa-users"/>

    <ul class="navigation">
        <c:forEach items="${menu}" var="item">
            <c:set var="active" value="${requestURL == item.key ? 'active' : '' }" />
            <li class="${active}">
                <a href="${item.key}">
                    <i class="${item.value}"></i>
                </a>
            </li>
        </c:forEach>
    </ul>
</div>
