<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericuserpage>
    <jsp:attribute name="title">
      Easypass - Création d'un groupe
    </jsp:attribute>
    <jsp:body>
        <div class="container">
            <div class="panel-heading">
               <div class="panel-title">
                    <h3 class="title">Créer un groupe</h3>
                </div>
            </div> 
            <div class="col-sm-5">
                <c:url value="${formAction}" var="submitURL"/>
                <form action="${submitURL}" method="POST" enctype="multipart/form-data">
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
                    <div class="form-group">
                        <label for="logo">Logo du groupe : </label>
                        <input type="file" name="logo" id="file" />
                    </div>
                    <div class="form-group">
                        <label for="users">Utilisateurs : </label>
                        <select data-live-search="true" name="users" class="selectpicker form-control" multiple>
                                <c:forEach var="user" items="${users}">
                                    <option value="${user.getId()}">${user.getUsername()}</option>
                                </c:forEach>
                        </select> 
                    </div>
                    <div class="form-group text-center">
                        <button type="submit" class="btn btn-primary">Enregistrer</button>
                        <a class="btn btn-success" href="${pageContext.request.contextPath}${FrontGroupServlet.URL_BASE}">Retour</a>
                    </div>
                </form>
            </div>
          </div>
    </jsp:body>
</t:genericuserpage>