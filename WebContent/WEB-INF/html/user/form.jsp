<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url value="${formAction}" var="submitURL">
    <c:if test="${not empty user}">
        <c:param name="userId" value="${user.getId()}"/>
    </c:if>
</c:url>
<form action="${submitURL}" method="POST">
    <div class="form-group">
        <label for="username">Nom d'utilisateur : </label>
        <input type="text" name="username" class="form-control" value="${user.getUsername()}">
        <label for="lastname">Nom : </label>
        <input type="text" name="lastname" class="form-control" value="${user.getLastname()}">
        <label for="firstname">Prenom : </label>
        <input type="text" name="firstname" class="form-control" value="${user.getFirstname()}">
        <label for="email">Email : </label>
        <input type="text" name="email" class="form-control" value="${user.getEmail()}">
        <label for="password">Mot de passe : </label>
        <input type="password" name="password" class="form-control" value="${user.getPassword()}">
    </div>
    <br />
    <button type="submit" class="btn btn-default">Enregistrer</button>
</form>