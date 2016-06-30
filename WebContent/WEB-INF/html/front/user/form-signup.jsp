<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url value="${formAction}" var="submitURL">
</c:url>

<div class="col-sm-4 col-md-offset-4">
    <form action="${submitURL}" method="POST" class="form-horizontal">
        <div class="form-group ${not empty errors.get('username')?'has-error':''}">
            <label class="control-label" for="username">Nom d'utilisateur : </label>
            <div class="input-group"> 
                <span class="input-group-addon"><i class="fa fa-user" aria-hidden="true"></i></span>
                <input type="text" name="username" class="form-control" value="${user.getUsername()}">
            </div>
            <c:if test="${not empty errors.get('username')}">
                <small class="help-block">${errors.get("username")}</small>
            </c:if>
        </div>
        
        <div class="form-group ${not empty errors.get('lastname')?'has-error':''}">
            <label class="control-label" for="lastname">Nom : </label>
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-user" aria-hidden="true"></i></span> 
                <input type="text" name="lastname" class="form-control" value="${user.getLastname()}">
            </div>
            <c:if test="${not empty errors.get('lastname')}">
                <small class="help-block">${errors.get("lastname")}</small>
            </c:if>
        </div>
        
        <div class="form-group ${not empty errors.get('firstname')?'has-error':''}">
            <label class="control-label" for="firstname">Prenom : </label>
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-user" aria-hidden="true"></i></span>
                <input type="text" name="firstname" class="form-control" value="${user.getFirstname()}">
            </div>
            <c:if test="${not empty errors.get('firstname')}">
                <small class="help-block">${errors.get("firstname")}</small>
            </c:if>
        </div>
        
        <div class="form-group ${not empty errors.get('email')?'has-error':''}">
            <label class="control-label" for="email">Email : </label>
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-envelope" aria-hidden="true"></i></span>
                <input type="text" name="email" class="form-control" value="${user.getEmail()}">
            </div>
            <c:if test="${not empty errors.get('email')}">
                <small class="help-block">${errors.get("email")}</small>
            </c:if>
        </div>
        <div class="form-group ${not empty errors.get('password')?'has-error':''}">
            <label class="control-label" for="password">Mot de passe : </label>
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-lock" aria-hidden="true"></i></span> 
                <input type="password" name="password" class="form-control" value="${user.getPassword()}">
            </div>
            <c:if test="${not empty errors.get('password')}">
                <small class="help-block">${errors.get("password")}</small>
            </c:if>
        </div>
        <div class="form-group text-center">
            <button type="submit" class="btn btn-primary">Je m'inscris</button>
        </div>
    </form>
</div>
