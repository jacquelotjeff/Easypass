<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url value="${formAction}" var="submitURL">
    <c:if test="${not empty password}">
        <c:param name="passwordId" value="${password.getId()}"/>
    </c:if>
</c:url>

<form action="${submitURL}" method="POST">
    <div class="form-group">
        <div class="form-group row ${not empty errors.get('title')?'has-error':''}">
            <div class="col-sm-5">
                <label for="title">Titre : </label>
                <input type="text" name="title" class="form-control" value="${password.getTitle()}">
                <c:if test="${not empty errors.get('title')}">
                    <small class="help-block">${errors.get("title")}</small>
                </c:if>
            </div>
        </div>
        <div class="form-group row ${not empty errors.get('siteUrl')?'has-error':''}">
            <div class="col-sm-4">
                <label for="site">Site : </label> 
                <input type="text" name="site" class="form-control" value="${password.getSiteUrl()}">
                <c:if test="${not empty errors.get('siteUrl')}">
                    <small class="help-block">${errors.get("siteUrl")}</small>
                </c:if>
            </div>
        </div>
        <div class="form-group row ${not empty errors.get('password')?'has-error':''}">
            <div class="col-sm-4">
            <label for="password">Mot de passe : </label>
                <div class="input-group"> 
                    <input name="password" type="password" class="form-control password-field" value="${password.getPassword()}">
                    <div class="input-group-addon show-password">
                        <span class="glyphicon glyphicon-eye-open"></span>
                    </div>
                </div>
                <c:if test="${not empty errors.get('password')}">
                    <small class="help-block">${errors.get("password")}</small>
                </c:if>
            </div>
        </div>
        <div class="form-group row ${not empty errors.get('category')?'has-error':''}">
            <div class="col-sm-4">
                <label for="categoryId">Catégorie : </label> 
                <select name="categoryId" class="form-control selectpicker">
                    <c:forEach var="category" items="${categories.values()}">
                        <option 
                            value="${category.getId()}"
                            <c:if test="${password.getCategory() == category.getId()}">selected</c:if>
                            >
                            ${category.getName()}
                        </option>
                    </c:forEach>
                </select>
                <c:if test="${not empty errors.get('category')}">
                    <small class="help-block">${errors.get("category")}</small>
                </c:if>
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-5">
                <label for="informations">Informations : </label> 
                <textarea name="informations" class="form-control">
                    ${password.getInformations()}
                </textarea>
                <c:if test="${not empty errors.get('informations')}">
                    <small class="help-block">${errors.get("informations")}</small>
                </c:if>
            </div>
        </div>
    </div>
    <br /> 
    <c:choose>
        <c:when test="${password == null}">
           <input type="hidden" name="ownerId" value='${ownerId}'>
            <input type="hidden" name="ownerType" value='${ownerType}'>
        </c:when>
        <c:otherwise>
            <input type="hidden" name="passwordId" value='${password.getId()}'>
        </c:otherwise>
    </c:choose>
    <div class="col-sm-12 text-right">
        <a class="btn btn-success" href="${pageContext.request.contextPath}${FrontPasswordServlet.URL_BASE}">Retour</a>
        <button type="submit" class="btn btn-primary">Enregistrer</button>
    </div>
</form>