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
                    <c:url value="${formAction}" var="submitURL"/>
                    <form action="${submitURL}" method="POST">
                        <div class='form-group ${not empty errors.get("name")?"has-error":""}'>
                            <label for="name">Nom du groupe : </label> 
                            <input type="text" name="name" class="form-control" value="${group.getName()}">
                            <c:if test="${not empty errors.get('name')}">
                                <small class="help-block">${errors.get("name")}</small>
                            </c:if>
                        </div>
                        <div class='form-group ${not empty errors.get("description")?"has-error":""}'>
                            <label for="description">Description du groupe : </label> 
                            <textarea name="description" class="form-control">
                                ${group.getDescription()}
                            </textarea>
                            <c:if test="${not empty errors.get('description')}">
                                <small class="help-block">${errors.get("description")}</small>
                            </c:if>
                        </div>
                        <div class='form-group ${not empty errors.get("logo")?"has-error":""}'> 
                            <label for="logo">Logo du groupe : </label> 
                            <input type="text" name="logo" class="form-control" value="${group.getLogo()}">
                            <c:if test="${not empty errors.get('logo')}">
                                <small class="help-block">${errors.get("logo")}</small>
                            </c:if>
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