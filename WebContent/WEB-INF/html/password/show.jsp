<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Consulter le mot de passe pour le site <c:out value="${password.getSiteUrl()}"/>
    </jsp:attribute>
    <jsp:body> 
        <h1>Consulter le mot de passe pour le site de <c:out value="${password.getSiteUrl()}"/></h1>
        <p>Titre : <c:out value="${password.getNom()}"/></p>
        <p>Mot de passe : <c:out value="${password.getPassword()}"/></p>
        
        <a class="btn btn-success" href="liste">Retour</a>
        <a class="btn btn-default" href="#">Editer</a>
        <a class="btn btn-danger" href="#">Supprimer</a>
    </jsp:body>
</t:genericadminpage>