<%@page import="fr.easypass.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Liste des mots de passe
    </jsp:attribute>
    <jsp:body>
        <h1>Exemple liste des mots de passe de l'utilisateur</h1>
        <table class="table table-striped table-hover table-users">
            <thead>
                <tr>
                    <th>Titre</th>
                    <th>Site</th>
                    <th>mot de passe</th>
                </tr>
            </thead>
    
            <tbody>
                <c:forEach var="password" items="${passwords}">
                    <tr>
                        <td>${password.getNom()}</td>
                        <td>${password.getSiteUrl()}</td>
                        <td>${password.getPassword()}</td>
                        <c:url value="voir" var="showURL">
                            <c:param name="password"   value="${password.getNom()}" />
                        </c:url>
                        <td><a class="btn btn-success" href='<c:out value="${showURL}"/>'>Voir</a></td>
                        <c:url value="editer" var="editURL">
                            <c:param name="password"   value="${password.getNom()}" />
                        </c:url>
                        <td><a class="btn btn-default" href="<c:out value="${editURL}"/>">Editer</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="creer" class="btn btn-primary">Créer</a>
    </jsp:body>
</t:genericadminpage>