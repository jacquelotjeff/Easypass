<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.easypass.servlets.UserServlet"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Consulter le profil de <c:out value="${user.getUsername()}"/>
    </jsp:attribute>
    <jsp:body> 
        
        <h1>Consulter le profil de <c:out value="${user.getUsername()}"/></h1>
        <p>Prenom : <c:out value="${user.getFirstname()}"/></p>
        <p>Nom : <c:out value="${user.getLastname()}"/></p>
        <p>Email : <c:out value="${user.getEmail()}"/></p>
        
        <a class="btn btn-success" href="${UserServlet.baseURL}">Retour</a>
        
        <c:url value="editer" var="editURL">
            <c:param name="userId"   value="${user.getId()}" />
        </c:url>
        <a class="btn btn-default" href="${editURL}">Editer</a>
        
        <c:url value="utilisateurs/supprimer" var="deleteURL">
            <c:param name="userId"   value="${user.getId()}" />
        </c:url>
        <form action="${deleteURL}" method="POST">
            <button type="submit" class="btn btn-danger">Supprimer</button>
        </form>
    </jsp:body>
</t:genericadminpage>