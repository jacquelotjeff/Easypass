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
        <c:choose>
          <c:when test="${not empty group}">
            <div class="col-sm-6">
                <label>Utilisateurs disponibles</label>
                <ul class="list-group">
                  <c:forEach var="user" items="${users}">
                      <li class="list-group-item">
                        <span>${user.getUsername()}</span>
                        <c:url value="ajouter-utilisateur" var="addUserURL">
                            <c:param name="userId"   value="${user.getId()}" />
                        </c:url>
                        <form action="${addUserURL}" method="POST">
                            <button type="submit" class="btn btn-default">Ajouter</button>
                        </form>
                      </li>
                  </c:forEach>
                </ul>
            </div>
            <div class="col-sm-6">
                <label>Utilisateurs du groupe</label>
                <ul class="list-group">
                  <c:forEach var="user" items="${groupUsers}">
                      <li class="list-group-item">
                        <span>${user.getUsername()}</span>
                        <c:url value="supprimer-utilisateur" var="deleteURL">
                            <c:param name="userId"   value="${user.getId()}" />
                        </c:url>
                        <form action="${deleteURL}" method="POST">
                            <button type="submit" class="btn btn-danger">Supprimer</button>
                        </form>
                      </li>
                  </c:forEach>
                </ul>
            </div>
          </c:when>
          <c:when test="${empty group}">
            <select name="users" multiple>
              <c:forEach var="user" items="${users}">
                <option value="${user.getId()}">${user.getUsername()}</option>    
              </c:forEach>
            </select>
          </c:when>
        </c:choose>
        
    </div>
    <br />
    <button type="submit" class="btn btn-default">Enregistrer</button>
</form>