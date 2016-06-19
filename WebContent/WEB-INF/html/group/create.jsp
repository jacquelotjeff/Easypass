<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericpage>
    <jsp:attribute name="title">
      Easypass - Création d'un groupe
    </jsp:attribute>
    <jsp:body>
        <div class="jumbotron">
            <div class="container">
                <h1>Créer un groupe</h1>
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
                            <label for="name">Nom du groupe : </label> 
                            <input type="text" name="name" class="form-control" value="${group.getName()}">
                        </div>
                        <div class="form-group">
                            <label for="description">Description du groupe : </label> 
                            <input type="textarea" name="description" class="form-control" value="${group.getDescription()}">
                        </div>
                        <div class="form-group"> 
                            <label for="logo">Logo du groupe : </label> 
                            <input type="text" name="logo" class="form-control" value="${group.getLogo()}">
                        </div>
                        <div class="form-group">
                            <select name="users" class="selectpicker" multiple>
                                    <c:forEach var="user" items="${users}">
                                        <option value="${user.getId()}">${user.getUsername()}</option>
                                    </c:forEach>
                            </select> 
                        </div>
                        <button type="submit" class="btn btn-default">Enregistrer</button>
                    </form>
                  </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:genericpage>