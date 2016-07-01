<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="fr.easypass.servlets.front.FrontGroupServlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericuserpage>
    <jsp:attribute name="title">
      Easypass - Consulter le groupe <c:out value="${group.getName()}"/>
    </jsp:attribute>
    <jsp:body> 
        
        <h3>Consulter le groupe ${group.getName()}</h3>
        <p>Nom : ${group.getName()}</p>
        <p>Description : ${group.getDescription()}</p>
        <p>Logo : ${group.getLogo()}</p>
        
        <div class="col-sm-6">
            <ul class="list-group">
                <li class="list-group-item title">Membres</li>
                <c:forEach var="user" items="${users.values()}">
                 <li class="list-group-item text-left">
                    <img class="img-thumbnail" src="http://bootdey.com/img/Content/User_for_snippets.png">
                    <label class="name">${user.getUsername()}<br></label> 
                    <div class="break"></div>
                  </li>
                </c:forEach>
            </ul>
        </div>
        <ul class="list-group">
            <c:forEach var="password" items="${passwords.values()}">
                    <li class="list-group-item title">
                        <span class="col-sm-2">
                            <c:set var="category" value="${categories.get(password.getCategory())}"/>
                            <img class="thumbnail" alt="Logo de la catégorie ${category.getName()}" src="${category.getLogo()}">
                        </span>
                        <div class="col-sm-6">
                            <h4 class="list-group-item-heading">${password.getTitle()}</h4>
                            <p class="list-group-item-text">
                                ${password.getInformations()}
                            </p>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <input type="password" class="form-control password-field" value="${password.getPassword()}">
                                <div class="input-group-addon show-password">
                                    <span class="fa fa-eye"></span>
                                </div>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </li>
            </c:forEach>
        </ul>
        
        <div class="col-sm-12">
            <a class="btn btn-success" href="${pageContext.request.contextPath}${FrontGroupServlet.prefixURL}">Retour</a>
            <c:if test="${groupAdmins.containsKey(sessionScope.user.getId())}">
                <c:url value="editer" var="editURL">
                    <c:param name="userId"   value="${group.getId()}" />
                </c:url>
                <a class="btn btn-default" href="${editURL}">Editer</a>
                <c:url value="groupes/supprimer" var="deleteURL">
                    <c:param name="userId"   value="${group.getId()}" />
                </c:url>
                <form action="${deleteURL}" method="POST">
                    <button type="submit" class="btn btn-danger">Supprimer</button>
                </form>
            </c:if>
        </div>
    </jsp:body>
</t:genericuserpage>