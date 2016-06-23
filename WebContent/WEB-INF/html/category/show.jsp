<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="fr.easypass.servlets.CategoryServlet"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Consulter la catégorie ${category.getName()}
    </jsp:attribute>
    <jsp:body> 
        
        <h1>Consulter la catégorie ${category.getName()}</h1>
        <p>Nom : ${category.getName()}</p>
        <p>Logo : ${user.getLogo()}</p>
        
        <a class="btn btn-success" href="${CategoryServlet.baseURL}">Retour</a>
        
        <c:url value="editer" var="editURL">
            <c:param name="categoryId"   value="${category.getId()}" />
        </c:url>
        <a class="btn btn-default" href="${editURL}">Editer</a>
        
        <c:url value="utilisateurs/supprimer" var="deleteURL">
            <c:param name="categoryId"   value="${category.getId()}" />
        </c:url>
        <form action="${deleteURL}" method="POST">
            <button type="submit" class="btn btn-danger">Supprimer</button>
        </form>
    </jsp:body>
</t:genericadminpage>