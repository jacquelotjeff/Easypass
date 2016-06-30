<%@ page import="fr.easypass.model.User"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Liste des groupes
    </jsp:attribute>
    <jsp:body>
        <h1>Liste des groupes</h1>
        <table class="table table-striped table-hover table-users">
            <thead>
                <tr>
                    <th>Nom</th>
                    <th>Description</th>
                    <th>Logo</th>
                    <th class="col-sm-2">Action</th>
                </tr>
            </thead>
    
            <tbody>
                <c:forEach var="group" items="${groups}">
                    <tr>
                        <td>${group.getName()}</td>
                        <td>${group.getDescription()}</td>
                        <td>${group.getLogo()}</td>
                        <td> 
                            <c:url value="groupes/supprimer" var="deleteURL">
                                <c:param name="groupId"   value="${group.getId()}" />
                            </c:url>
                            <form action="${deleteURL}" method="POST">
                                <div class="btn-group"> 
                                    <c:url value="groupes/voir" var="showURL">
                                        <c:param name="groupId"   value="${group.getId()}" />
                                    </c:url>
                                    <a role="button" class="btn btn-success" href='${showURL}'>
                                        <i class="fa fa-eye"></i>
                                    </a>
                                    
                                    <c:url value="groupes/editer" var="editURL">
                                        <c:param name="groupId"   value="${group.getId()}" />
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
        <a href="groupes/creer" class="btn btn-primary">Créer</a>
    </jsp:body>
</t:genericadminpage>