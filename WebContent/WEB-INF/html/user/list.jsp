<%@ page import="fr.easypass.model.User"%>
<%@ page import="fr.easypass.servlets.BaseServlet"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Liste des utilisateurs
    </jsp:attribute>
    <jsp:body>
        <h1>Liste des utilisateurs</h1>
        <table class="table table-striped table-hover table-users">
            <thead>
                <tr>
                    <th>Utilisateur</th>
                    <th>Nom</th>
                    <th>Prenom</th>
                    <th>Email</th>
                    <th class="col-sm-2">Action</th>
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
                            <c:url value="utilisateurs/supprimer" var="deleteURL">
                                <c:param name="userId"   value="${user.getId()}" />
                            </c:url>
                            <form action="${deleteURL}" method="POST">
                                <div class="btn-group">

                                    <c:url value="utilisateurs/voir" var="showURL">
                                        <c:param name="userId"   value="${user.getId()}" />
                                    </c:url>
                                    <a role="button" class="btn btn-success" href='<c:out value="${showURL}"/>'>
                                        <i class="fa fa-eye"></i>
                                    </a>

                                    <c:url value="utilisateurs/editer" var="editURL">
                                        <c:param name="userId"   value="${user.getId()}" />
                                    </c:url>
                                    <a role="button" class="btn btn-primary" href="<c:out value="${editURL}"/>">
                                        <i class="fa fa-edit"></i>
                                    </a>

                                    <button type="submit" class="btn btn-danger">
                                        <i class="fa fa-remove"></i>
                                    </button>
                                </div>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="${BaseServlet.rootPath}/inscription" class="btn btn-primary">Créer</a>
    </jsp:body>
</t:genericadminpage>