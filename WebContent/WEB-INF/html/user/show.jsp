<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Consulter le profil de <c:out value="${user.getUsername()}"/>
    </jsp:attribute>
    <jsp:body> 
        <h1>Consulter le profil de <c:out value="${user.getUsername()}"/></h1>
        <p>Prenom : <c:out value="${user.getFirstname()}"/></p>
        <p>Nom : <c:out value="${user.getLastname()}"/></p>
        <p>Email : <c:out value="${user.getEmail()}"/></p>
        
        <a class="btn btn-success" href="liste">Retour</a>
        <a class="btn btn-default" href="#">Editer</a>
        <a class="btn btn-danger" href="#">Supprimer</a>
    </jsp:body>
</t:genericadminpage>