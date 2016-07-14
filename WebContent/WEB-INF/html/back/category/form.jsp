<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url value="${formAction}" var="submitURL">
    <c:if test="${not empty category}">
        <c:param name="categoryId" value="${category.getId()}"/>
    </c:if>
</c:url>
<form action="${submitURL}" method="POST" enctype="multipart/form-data">
	<div class="form-group ${not empty errors.get('name')?'has-error':''}">
   		<label for="name">Nom : </label>
		<input type="text" name="name" class="form-control" value='<c:out value="${category.getName()}"/>'>
        <c:if test="${not empty errors.get('name')}">
	    	<small class="help-block">${errors.get("name")}</small>
    	</c:if>
    </div>
   	<div class="form-group">
        <label for="logo">Logo de la catégorie : </label>
        <input type="file" name="logo" id="file" />
	</div>
    <c:if test="${not empty category.getLogo()}">
        <label for="logo">Logo actuel : </label>
        <img width=250 class="thumbnail" alt="Logo de la catégorie ${category.getName()}" src="${pageContext.servletContext.contextPath}/fichier?nom=${category.getLogo()}">
    </c:if>
    
     <br />
    <button type="submit" class="btn btn-default">Enregistrer</button>
</form>