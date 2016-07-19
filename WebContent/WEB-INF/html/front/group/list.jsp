<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.easypass.servlets.front.FrontUserServlet"%>
<%@ page import="fr.easypass.model.Password"%>

<t:genericuserpage>
    <jsp:attribute name="title">
      Easypass - Consulter le profil de <c:out value="${user.getUsername()}"/>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="${pageContext.request.contextPath}/js/password-spoiler.js"></script>
    </jsp:attribute>
    <jsp:body>
        <h3>Mes groupes</h3>
        <ul class="list-group">
            <c:forEach var="group" items="${groups}">
                <li class="list-group-item title">
                        <span class="col-sm-2">
                            <img class="thumbnail" alt="Logo du groupe ${group.getName()}" src="${pageContext.servletContext.contextPath}/fichier?nom=${group.getLogo()}">
                        </span>
                        <div class="col-sm-6">
                            <h4 class="list-group-item-heading">${group.getName()}</h4>
                            <p class="list-group-item-text">
                                ${group.getDescription()}
                            </p>
                        </div>
                        <div class="col-sm-4">
                            <c:url value="/utilisateur/groupes/voir" var="showGroupURL">
                                <c:param name="groupId" value="${group.getId()}"/>
                            </c:url>
                            <a href="${showGroupURL}" title="Voir le groupe." class="btn btn-success btn-sm">
                                <i class="fa fa-eye"></i>
                            </a>
                            <c:url value="/utilisateur/groupes/editer" var="editGroupURL">
                                <c:param name="groupId" value="${group.getId()}"/>
                            </c:url>
                            <c:if test="${groupsAdmin.containsKey(group.getId())}">
                                <a href="${editGroupURL}" title="Editer le groupe." class="btn btn-primary btn-sm">
                                    <i class="fa fa-edit"></i>
                                </a>
                            </c:if>
                        </div>
                        <div class="clearfix"></div>
                    </li>
            </c:forEach>
            <c:url value="/utilisateur/groupes/creer" var="addGroupURL">
            </c:url>
            <li class="list-group-item title">
                <a href="${addGroupURL}" class="btn btn-md btn-primary">
                    <i class="fa fa-plus"></i>
                    Ajouter un groupe
                </a>
            </li>  
        </ul>
    </jsp:body>
</t:genericuserpage>