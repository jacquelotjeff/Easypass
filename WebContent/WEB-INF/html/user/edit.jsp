<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="fr.easypass.model.Password" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Edition de l'utilisateur <c:out value="${user.getUsername()}"/>
    </jsp:attribute>
    <jsp:body>
        <h1>Editer l'utilisateur <c:out value="${user.getUsername()}"/></h1>
        <jsp:include page="form.jsp"/>
        <div class="col-sm-6">
            <ul class="list-group">
                <li class="list-group-item title">Mots de passe de l'utilisateur</li>
                <c:forEach var="password" items="${userPasswords}">
                    <li class="list-group-item title">${password.getTitle()}</li>
                </c:forEach>
                <c:url value="/admin/mot-de-passe/creer" var="addPasswordURL">
                    <c:param name="ownerId"
                        value="${user.getId()}" />
                    <c:param name="ownerType"
                        value="${Password.OWNER_TYPE_USER}" />
                </c:url> 
                <li class="list-group-item title">
                    <a href="${addPasswordURL}" class="btn btn-block btn-primary">
                        <i class="fa fa-plus"></i>
                        Ajouter un mot de passe
                    </a>
                </li>
            </ul>
        </div>
    </jsp:body>
</t:genericadminpage>