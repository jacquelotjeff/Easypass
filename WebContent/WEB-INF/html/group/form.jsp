<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url value="${formAction}" var="submitURL">
    <c:if test="${not empty group}">
        <c:param name="groupId" value="${group.getId()}"/>
    </c:if>
</c:url>
<form action="${submitURL}" method="POST">
    <div class="form-group">
        <label for="name">Nom du groupe : </label>
        <input type="text" name="name" class="form-control" value="${group.getName()}">
        <label for="description">Description du groupe : </label>
        <input type="textarea" name="description" class="form-control" value="${group.getDescription()}">
        <label for="logo">Logo du groupe : </label>
        <input type="text" name="logo" class="form-control" value="${group.getLogo()}">
        <label for="users">Utilisateurs : </label>
        <select name="users" multiple>
          <c:forEach var="user" items="${users}">
            <option value="${user.getId()}">${user.getUsername()}</option>    
          </c:forEach>
        </select>
    </div>
    <br />
    <button type="submit" class="btn btn-default">Enregistrer</button>
</form>