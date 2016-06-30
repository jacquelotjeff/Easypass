<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="fr.easypass.model.Password" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericuserpage>
    <jsp:attribute name="title">
      Easypass - Modification de ${group.getName()}
    </jsp:attribute>
    <jsp:body>
        <h1>Modification de ${group.getName()}</h1>
        <div class="panel panel-default">
          <div class="panel-heading">Infos générales du groupe</div>
          <div class="panel-body">
            <c:url value="${formAction}" var="submitURL">
                <c:if test="${not empty group}">
                    <c:param name="groupId" value="${group.getId()}" />
                </c:if>
            </c:url>
            <form action="${submitURL}" method="POST">
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
                    <input type="text" name="logo" class="form-control" value="${group.getLogo()}">
                </div>                    
                    
                
                <br />
                <button type="submit" class="btn btn-default">Enregistrer</button>
            </form>
          </div>
        </div>
        
        <div class="col-sm-6">
            <ul class="list-group">
                <li class="list-group-item title">Utilisateurs disponibles</li>
                <c:forEach var="user" items="${users}">
                 <li class="list-group-item text-left">
                    <img class="img-thumbnail" src="http://bootdey.com/img/Content/User_for_snippets.png">
                    <label class="name">${user.getUsername()}<br></label> 
                    <span class="pull-right">
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
                    </span>
                    <div class="break"></div>
                   </li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-sm-6">
        
            <ul class="list-group">
                <li class="list-group-item title">Membres du groupe</li>
                <c:forEach var="user" items="${groupUsers}">
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
                <li class="list-group-item title">Mots de passe du groupe</li>
                <c:forEach var="password" items="${groupPasswords}">
                    <li class="list-group-item title">${password.getTitle()}</li>
                </c:forEach>
                <c:url value="/admin/mot-de-passe/creer" var="addPasswordURL">
                    <c:param name="ownerId"
                        value="${group.getId()}" />
                    <c:param name="ownerType"
                        value="${Password.OWNER_TYPE_GROUP}" />
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
</t:genericuserpage>