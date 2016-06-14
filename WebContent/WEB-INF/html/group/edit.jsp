<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
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
                    <label for="name">Nom du groupe : </label> <input type="text"
                        name="name" class="form-control" value="${group.getName()}">
                    <label for="description">Description du groupe : </label> <input
                        type="textarea" name="description" class="form-control"
                        value="${group.getDescription()}"> <label for="logo">Logo
                        du groupe : </label> <input type="text" name="logo"
                        class="form-control" value="${group.getLogo()}">
                </div>
                <br />
                <button type="submit" class="btn btn-default">Enregistrer</button>
            </form>
          </div>
        </div>
        
        <div class="col-sm-6">
            <li href="#" class="list-group-item title">Utilisateurs disponibles</li>
            <ul class="list-group">
                <c:forEach var="user" items="${users}">
                 <li href="#" class="list-group-item text-left">
                    <img class="img-thumbnail" src="http://bootdey.com/img/Content/User_for_snippets.png">
                    <label class="name">${user.getUsername()}<br></label> 
                    <label class="pull-right">
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
                    </label>
                    <div class="break"></div>
                   </li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-sm-6">
        
            <ul class="list-group">
                <li href="#" class="list-group-item title">Membres du groupe</li>
                <c:forEach var="user" items="${groupUsers}">
                     <c:choose>
                        <c:when test="${groupAdmins.containsKey(user.getId())}">
                            <%@include file="manage-user/manage-admin.jsp"%>
                        </c:when>
                        <c:otherwise>
                            <%@include file="manage-user/manage-user.jsp"%>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </div>
    </jsp:body>
</t:genericadminpage>