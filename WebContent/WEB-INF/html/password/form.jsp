<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form action="${formAction}" method="POST">
    <div class="form-group">
        <div class="form-group">
            <label for="title">Nom : </label>
            <input type="text" name="title" class="form-control">
        </div>
        <div class="form-group">
            <label for="site">Site : </label>
            <input type="text" name="site" class="form-control" value="">
        </div>
        <div class="form-group">
            <label for="password">Mot de passe : </label>
            <input type="password" name="password" class="form-control" value="">
        </div>
        <div class="form-group">
            <label for="categoryId">Catégorie : </label>
            <select name="categoryId" class="selectpicker">
                <c:forEach var="category" items="${categories}">
                    <option value="${category.getId()}">${category.getName()}</option>
                </c:forEach>
            </select> 
        </div>
        <div class="form-group">
            <label for="informations">Informations : </label>
            <input type="text" name="informations" class="form-control" value="">
        </div>
    </div>
    <br />
    <input type="hidden" name="ownerId" value='${ownerId}'>
    <input type="hidden" name="ownerType" value='${ownerType}'>
    <button type="submit" class="btn btn-default">Enregistrer</button>
</form>