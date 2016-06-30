<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url value="${formAction}" var="submitURL">
    <c:if test="${not empty user}">
        <c:param name="userId" value="${user.getId()}" />
    </c:if>
</c:url>
<form action="${submitURL}" method="POST">
    <div class="form-group">
        <div
            class="form-group ${not empty errors.get('username')?'has-error':''}">
            <label class="control-label" for="username">Nom
                d'utilisateur : </label> <input type="text" name="username"
                class="form-control" value="${user.getUsername()}">
            <c:if test="${not empty errors.get('username')}">
                <small class="help-block">${errors.get("username")}</small>
            </c:if>
        </div>
        <div
            class="form-group ${not empty errors.get('lastname')?'has-error':''}">
            <label class="control-label" for="lastname">Nom : </label> <input
                type="text" name="lastname" class="form-control"
                value="${user.getLastname()}">
            <c:if test="${not empty errors.get('lastname')}">
                <small class="help-block">${errors.get("lastname")}</small>
            </c:if>
        </div>
        <div
            class="form-group ${not empty errors.get('firstname')?'has-error':''}">
            <label class="control-label" for="firstname">Prenom
                : </label> <input type="text" name="firstname"
                class="form-control" value="${user.getFirstname()}">
            <c:if test="${not empty errors.get('firstname')}">
                <small class="help-block">${errors.get("firstname")}</small>
            </c:if>
        </div>
        <div
            class="form-group ${not empty errors.get('email')?'has-error':''}">
            <label class="control-label" for="email">Email : </label> <input
                type="text" name="email" class="form-control"
                value="${user.getEmail()}">
            <c:if test="${not empty errors.get('email')}">
                <small class="help-block">${errors.get("email")}</small>
            </c:if>
        </div>
        <div
            class="form-group ${not empty errors.get('password')?'has-error':''}">
            <label class="control-label" for="password">Mot de
                passe : </label> <input type="password" name="password"
                class="form-control" value="${user.getPassword()}">
            <c:if test="${not empty errors.get('password')}">
                <small class="help-block">${errors.get("password")}</small>
            </c:if>
        </div>
    </div>
    <br />
    <button type="submit" class="btn btn-default">Enregistrer</button>
</form>