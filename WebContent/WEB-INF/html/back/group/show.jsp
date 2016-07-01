<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="fr.easypass.servlets.back.BackGroupServlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Consulter le groupe <c:out value="${group.getName()}"/>
    </jsp:attribute>
    <jsp:body> 
        
        <h1>Consulter le groupe <c:out value="${group.getName()}"/></h1>
        <p>Nom : <c:out value="${group.getName()}"/></p>
        <p>Description : <c:out value="${group.getDescription()}"/></p>
        <p>Logo : <c:out value="${group.getLogo()}"/></p>
        
        <a class="btn btn-success" href="${pageContext.request.contextPath}${GroupServlet.prefixURL}">Retour</a>
        
        <c:url value="editer" var="editURL">
            <c:param name="userId"   value="${group.getId()}" />
        </c:url>
        <a class="btn btn-default" href="${editURL}">Editer</a>
        
        <c:url value="groupes/supprimer" var="deleteURL">
            <c:param name="userId"   value="${group.getId()}" />
        </c:url>
        <form action="${deleteURL}" method="POST">
            <button type="submit" class="btn btn-danger">Supprimer</button>
        </form>
    </jsp:body>
</t:genericadminpage>