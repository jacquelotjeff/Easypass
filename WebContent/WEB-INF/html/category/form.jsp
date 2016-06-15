<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url value="${formAction}" var="submitURL">
    <c:if test="${not empty category}">
        <c:param name="categoryId" value="${category.getId()}"/>
    </c:if>
</c:url>
<form action="${submitURL}" method="POST">
    <div class="form-group">
        <label for="name">Nom : </label>
        <input type="text" name="name" class="form-control" value="${category.getName()}">
    </div>
    <div class="form-group">
        <label for="logo">Logo : </label>
        <input type="text" name="logo" class="form-control" value="${category.getLogo()}">
    </div>
    <br />
    <button type="submit" class="btn btn-default">Enregistrer</button>
</form>