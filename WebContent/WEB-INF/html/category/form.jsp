<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url value="${formAction}" var="submitURL">
    <c:if test="${not empty category}">
        <c:param name="categoryId" value="${category.getId()}"/>
    </c:if>
</c:url>
<form action="${submitURL}" method="POST">
	<div class="form-group ${not empty errors.get('name')?'has-error':''}">
   		<label for="name">Nom : </label>
		<input type="text" name="name" class="form-control" value='<c:out value="${category.getName()}"/>'>
        <c:if test="${not empty errors.get('name')}">
	    	<small class="help-block">${errors.get("name")}</small>
    	</c:if>
    </div>
   	<div class="form-group ${not empty errors.get('logo')?'has-error':''}">
        <label for="logo">Logo de la catégorie : </label>
        <input type="text" name="logo" class="form-control" value='<c:out value="${category.getLogo()}"/>'>
           <c:if test="${not empty errors.get('logo')}">
	    	<small class="help-block">${errors.get("logo")}</small>
    	</c:if>
	</div>
     <br />
    <button type="submit" class="btn btn-default">Enregistrer</button>
</form>