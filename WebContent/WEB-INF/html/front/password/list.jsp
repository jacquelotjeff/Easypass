<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.easypass.servlets.back.BackUserServlet"%>
<%@ page import="fr.easypass.model.Password"%>

<t:genericuserpage>
    <jsp:attribute name="title">
      Easypass - Consulter le profil de <c:out value="${user.getUsername()}"/>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="${pageContext.request.contextPath}/js/password-spoiler.js"></script>
    </jsp:attribute>
    <jsp:body>
        <h3>Mots de passes</h3>
        <ul class="list-group">
            <c:forEach var="password" items="${passwords}">
                    <li class="list-group-item title">
                        <span class="col-sm-2">
                            <c:set var="category" value="${categories.get(password.getCategory())}"/>
                            <img class="thumbnail" alt="Logo de la catégorie ${category.getName()}" src="${category.getLogo()}">
                        </span>
                        <div class="col-sm-6">
                            <h4 class="list-group-item-heading">${password.getTitle()}</h4>
                            <p class="list-group-item-text">
                                ${password.getInformations()}
                            </p>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <input type="password" class="form-control password-field" value="${password.getPassword()}">
                                <div class="input-group-addon show-password">
                                    <span class="fa fa-eye"></span>
                                </div>
                                <c:url value="/utilisateur/mots-de-passes/editer" var="editPasswordURL">
                                    <c:param name="passwordId" value="${password.getId()}"/>
                                </c:url>
                                <a href="${editPasswordURL}" class="input-group-addon">
                                    <i class="fa fa-edit"></i>
                                </a>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </li>
            </c:forEach>
            <c:url value="/utilisateur/mots-de-passes/creer" var="addPasswordURL">
                    <c:param name="ownerId"
                        value="${user.getId()}" />
                    <c:param name="ownerType"
                        value="${Password.OWNER_TYPE_USER}" />
            </c:url>  
            <li class="list-group-item title">
                <a href="${addPasswordURL}" class="btn btn-md btn-primary">
                    <i class="fa fa-plus"></i>
                    Ajouter un mot de passe
                </a>
            </li>
        </ul>
    </jsp:body>
</t:genericuserpage>