<%@ page import="fr.easypass.model.User"%>
<%@ page import="fr.easypass.servlets.back.BackPasswordServlet"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Liste des mots de passe
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="${pageContext.request.contextPath}/js/password-spoiler.js"></script>
    </jsp:attribute>
    <jsp:body>
        <h1>Liste des mots de passes</h1>
        <table class="table table-striped table-hover table-users">
            <thead>
                <tr>
                    <th>Titre</th>
                    <th>Site</th>
                    <th class="col-sm-2">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="password" items="${passwords}">
                    <tr>
                        <td>${password.getTitle()}</td>
                        <td>${password.getSiteUrl()}</td>
                        <td> 
                            <c:url value="${BackPasswordServlet.URL_BASE}/supprimer" var="deleteURL">
                                <c:param name="passwordId"   value="${password.getId()}" />
                            </c:url>
                            <form action="${deleteURL}" method="POST">
                                <div class="btn-group"> 
                                    <c:url value="${BackPasswordServlet.URL_BASE}/editer" var="editURL">
                                        <c:param name="passwordId"   value="${password.getId()}" />
                                    </c:url>
                                    <a role="button" class="btn btn-primary" href="${editURL}">
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
    </jsp:body>
</t:genericadminpage>