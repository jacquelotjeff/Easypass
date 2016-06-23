<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form action="${formAction}" method="POST">
    <div class="form-group">
        <div class="form-group ${not empty errors.get('title')?'has-error':''}">
            <label for="title">Titre pour le mot de passe : </label> 
            <input type="text" name="title" class="form-control" value="${password.getTitle()}">
            <c:if test="${not empty errors.get('title')}">
                <small class="help-block">${errors.get("title")}</small>
            </c:if>
        </div>
        <div class="form-group ${not empty errors.get('siteUrl')?'has-error':''}">
            <label for="site">Site du mot de passe : </label> 
            <input type="text" name="site" class="form-control" value="${password.getSiteUrl()}">
            <c:if test="${not empty errors.get('siteUrl')}">
                <small class="help-block">${errors.get("siteUrl")}</small>
            </c:if>
        </div>
        <div class="form-group ${not empty errors.get('password')?'has-error':''}">
            <label for="password">Mot de passe : </label> 
            <input type="text" name="password" class="form-control" value="${password.getPassword()}">
            <c:if test="${not empty errors.get('password')}">
                <small class="help-block">${errors.get("password")}</small>
            </c:if>
        </div>
        <div class="form-group">
            <label for="categoryId">Catégorie : </label> <select
                name="categoryId" class="selectpicker">
                <c:forEach var="category" items="${categories}">
                    <option value="${category.getId()}">${category.getName()}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="informations">Informations : </label> 
            <textarea name="informations" class="form-control">
                ${password.getInformations()}
            </textarea>
            <c:if test="${not empty errors.get('informations')}">
                <small class="help-block">${errors.get("informations")}</small>
            </c:if>
        </div>
    </div>
    <br /> <input type="hidden" name="ownerId" value='${ownerId}'>
    <input type="hidden" name="ownerType" value='${ownerType}'>
    <button type="submit" class="btn btn-default">Enregistrer</button>
</form>