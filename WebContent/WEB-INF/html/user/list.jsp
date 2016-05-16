<%@page import="fr.easypass.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Liste des utilisateurs
    </jsp:attribute>
    <jsp:body>
        <h1>Exemple liste des utilisateurs</h1>
        <table class="table table-striped table-hover table-users">
            <thead>
                <tr>
                    <th>Utilisateur</th>
                    <th>Nom</th>
                    <th>Prenom</th>
                    <th>E-mail</th>
                    <th>#</th>
                    <th>#</th>
                    <th>#</th>
                </tr>
            </thead>
    
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.getUsername()}</td>
                        <td>${user.getLastname()}</td>
                        <td>${user.getFirstname()}</td>
                        <td>${user.getEmail()}</td>
                        <c:url value="voir" var="showURL">
                            <c:param name="username"   value="${user.getUsername()}" />
                        </c:url>
                        <td><a class="btn btn-success" href='<c:out value="${showURL}"/>'>Voir</a></td>
                        <c:url value="editer" var="editURL">
                            <c:param name="username"   value="${user.getUsername()}" />
                        </c:url>
                        <td><a class="btn btn-default" href="<c:out value="${editURL}"/>">Editer</a></td>
                        <td><a class="btn btn-danger" href="#">Supprimer</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </jsp:body>
</t:genericadminpage>