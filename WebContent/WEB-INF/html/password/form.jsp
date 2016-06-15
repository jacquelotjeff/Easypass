<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form action="login" method="POST">
    <div class="form-group">
        <div class="form-group">
            <label for="name">Nom : </label>
            <input type="text" name="name" class="form-control">
        </div>
        <div class="form-group">
            <label for="site">Site : </label>
            <input type="text" name="site" class="form-control" value="">
        </div>
        <div class="form-group">
            <label for="password">Mot de passe : </label>
            <input type="text" name="password" class="form-control" value="">
        </div>
        <div class="form-group">
            <label for="category">Catégorie : </label>
            <select name="category" class="selectpicker" multiple>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.getId()}">${category.getName()}</option>
                </c:forEach>
            </select> 
        </div>
    </div>
    <br />
    <button type="submit" class="btn btn-default">Enregistrer</button>
</form>