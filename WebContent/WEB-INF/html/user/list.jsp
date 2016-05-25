<%@ page import="fr.easypass.model.User"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
                    <th>Email</th>
                    <th>Groupes</th>
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
                        
                        <td>
                            <c:forEach var="groupname" items="${user.getGroupsNames()}">
                              ${groupname},  
                            </c:forEach>
                        </td>
                        
                        <td>
                            <c:url value="utilisateurs/voir" var="showURL">
                                <c:param name="userId"   value="${user.getId()}" />
                            </c:url>
                            <a class="btn btn-success" href='<c:out value="${showURL}"/>'>Voir</a>
                        </td>
                        
                        <td>
                            <c:url value="utilisateurs/editer" var="editURL">
                                <c:param name="userId"   value="${user.getId()}" />
                            </c:url>
                            <a class="btn btn-default" href="<c:out value="${editURL}"/>">Editer</a>
                        </td>
                        
                        <td>
                            <c:url value="utilisateurs/supprimer" var="deleteURL">
                                <c:param name="userId"   value="${user.getId()}" />
                            </c:url>
                            <form action="${deleteURL}" method="POST">
                                <button type="submit" class="btn btn-danger">Supprimer</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="utilisateurs/creer" class="btn btn-primary">Créer</a>
    </jsp:body>
</t:genericadminpage>