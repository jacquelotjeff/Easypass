<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="fr.easypass.model.Password" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericuserpage>
    <jsp:attribute name="title">
      Easypass - Modification de ${group.getName()}
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="${pageContext.request.contextPath}/js/password-spoiler.js"></script>
    </jsp:attribute>
    <jsp:body>
        <h3>Modification de ${group.getName()}</h3>
        <div class="col-sm-6">
            <div class="panel panel-default">
              <div class="panel-heading">Infos générales du groupe</div>
              <div class="panel-body">
                <c:url value="${formAction}" var="submitURL">
                    <c:if test="${not empty group}">
                        <c:param name="groupId" value="${group.getId()}" />
                    </c:if>
                </c:url>
                <form action="${submitURL}" method="POST" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="name">Nom du groupe : </label> <input type="text" name="name" class="form-control" value="${group.getName()}">
                    </div>
                    <div class="form-group">
                        <label for="description">Description du groupe : </label> 
                        <textarea name="description" class="form-control">
                            ${group.getDescription()}
                        </textarea>
                    </div>
                    <div class="form-group">
                        <label for="logo">Logo du groupe : </label>
                        <input type="file" name="logo" id="file" />
                    </div>
                    <c:if test="${not empty group.getLogo()}">
                        <label for="logo">Logo actuel : </label>
                        <img width=250 class="thumbnail" alt="Logo du groupe ${group.getName()}" src="${pageContext.servletContext.contextPath}/fichier?nom=${group.getLogo()}">
                    </c:if>                 
                    <button type="submit" class="btn btn-primary text-center">Enregistrer</button>
                </form>
              </div>
            </div>
        </div>
        <div class="col-sm-6">
            <ul class="list-group">
                <li class="list-group-item title">Mots de passe du groupe</li>
                <c:forEach var="password" items="${groupPasswords.values()}">
                    <li class="list-group-item title">
                        <span class="col-sm-2">
                            <c:set var="category" value="${categories.get(password.getCategory())}"/>
                            <img class="thumbnail" alt="Logo de la catégorie ${category.getName()}" src="${category.getLogo()}">
                        </span>
                        <div class="col-sm-10">
                            <h4 class="list-group-item-heading">${password.getTitle()}</h4>
                            <p class="list-group-item-text">
                                ${password.getInformations()}
                            </p>
                        </div>
                        <div class="col-sm-12">
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
                        value="${group.getId()}" />
                    <c:param name="ownerType"
                        value="${Password.OWNER_TYPE_GROUP}" />
                </c:url> 
                <li class="list-group-item title text-center">
                    <a href="${addPasswordURL}" class="btn btn-primary">
                        <i class="fa fa-plus"></i>
                        Ajouter un mot de passe
                    </a>
                </li>
            </ul>
        </div>
        <div class="row col-sm-12">
            <div class="col-sm-6">
                <ul class="list-group">
                    <li class="list-group-item title">Membres du groupe</li>
                    <c:forEach var="user" items="${groupUsers.values()}">
                         <c:choose>
                            <c:when test="${groupAdmins.containsKey(user.getId())}">
                                <%@include file="manage-user/manageAdmin.jsp"%>
                            </c:when>
                            <c:otherwise>
                                <%@include file="manage-user/manageUser.jsp"%>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </ul>
            </div>
            <div class="col-sm-6">
                <ul class="list-group">
                    <li class="list-group-item title">Utilisateurs disponibles</li>
                    <c:forEach var="user" items="${users.values()}">
                     <li class="list-group-item text-left">
                        <img class="img-thumbnail" src="http://bootdey.com/img/Content/User_for_snippets.png">
                        <label class="name">${user.getUsername()}<br></label> 
                        <div class="pull-right">
                            <c:url value="ajouter-utilisateur"
                                var="addUserURL">
                                <c:param name="userId"
                                    value="${user.getId()}" />
                                <c:param name="groupId"
                                    value="${group.getId()}" />
                            </c:url> 
                            <form action="${addUserURL}" method="POST">
                                <button type="submit"
                                    class="btn btn-success btn-sm fa fa-plus"></button>
                            </form>
                        </div>
                        <div class="break"></div>
                       </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        
    </jsp:body>
</t:genericuserpage>