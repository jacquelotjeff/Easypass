<%@page import="fr.easypass.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Liste des groupes
    </jsp:attribute>
    <jsp:body>
        <h1>Exemple liste des groupes</h1>
        <table class="table table-striped table-hover table-users">
            <thead>
                <tr>
                    <th>Nom</th>
                    <th>Description</th>
                    <th>Utilisateurs</th>
                    <th>Logo</th>
                    <th>#</th>
                    <th>#</th>
                    <th>#</th>
                </tr>
            </thead>
    
            <tbody>
                <c:forEach var="group" items="${groups}">
                    <tr>
                        <td>${group.getName()}</td>
                        <td>${group.getDescription()}</td>
                        <td>
                            <c:forEach var="username" items="${group.getUsersNames()}">
                            ${username},
                            </c:forEach>
                        </td>
                        <td>${group.getLogo()}</td>
                        <c:url value="voir" var="showURL">
                            <c:param name="groupname"   value="${group.getName()}" />
                        </c:url>
                        <td><a class="btn btn-success" href='<c:out value="${showURL}"/>'>Voir</a></td>
                        <c:url value="editer" var="editURL">
                            <c:param name="groupname"   value="${group.getName()}" />
                        </c:url>
                        <td><a class="btn btn-default" href="<c:out value="${editURL}"/>">Editer</a></td>
                        <td><a class="btn btn-danger" href="#">Supprimer</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="creer" class="btn btn-primary">Créer</a>
    </jsp:body>
</t:genericadminpage>