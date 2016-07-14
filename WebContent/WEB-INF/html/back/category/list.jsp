<%@ page import="fr.easypass.model.User"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Liste des categories
    </jsp:attribute>
    <jsp:body>
        <h1>Liste des categories</h1>
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>Nom</th>
                    <th>Logo</th>
                    <th class="col-sm-2">Action</th>
                </tr>
            </thead>
    
            <tbody>
                <c:forEach var="category" items="${categories}">
                    <tr>
                        <td>${category.getName()}</td>
                        <td>
                            <img width="30" class="thumbnail" alt="Logo de la catégorie ${category.getName()}" src="${pageContext.servletContext.contextPath}/fichier?nom=${category.getLogo()}">
                        </td>
                        <td>   
                            <c:url value="categories/supprimer" var="deleteURL">
                                <c:param name="categoryId"   value="${category.getId()}" />
                            </c:url>
                            <form action="${deleteURL}" method="POST">
                                <div class="btn-group">
                                    <c:url value="categories/voir" var="showURL">
                                        <c:param name="categoryId"   value="${category.getId()}" />
                                    </c:url>
                                    <a role="button" class="btn btn-success" href="${showURL}">
                                        <i class="fa fa-eye"></i>
                                    </a>
    
                                    <c:url value="categories/editer" var="editURL">
                                        <c:param name="categoryId"   value="${category.getId()}" />
                                    </c:url>
                                    <a role="button" class="btn btn-primary" href="${editURL}">
                                        <i class="fa fa-edit"></i>
                                    </a>
    
                                    <button type="submit" class="btn btn-danger">
                                        <i class="fa fa-remove"></i>
                                    </button>
                                </div>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="categories/creer" class="btn btn-primary">Créer</a>
    </jsp:body>
</t:genericadminpage>