<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="fr.easypass.servlets.back.BackUserServlet"%>
<%@ page import="fr.easypass.model.Password"%>

<t:genericuserpage>
    <jsp:attribute name="title">
      Easypass - Consulter le profil de <c:out value="${user.getUsername()}"/>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="${pageContext.request.contextPath}/js/password-spoiler.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div id="information" class="tab-pane active">
            <h3>Informations générales</h3>
            <p>Prenom : <c:out value="${user.getFirstname()}"/></p>
            <p>Nom : <c:out value="${user.getLastname()}"/></p>
            <p>Email : <c:out value="${user.getEmail()}"/></p>
            <div>
                <c:url value="utilisateur/editer" var="editURL">
                </c:url>
                <a class="btn btn-primary" href="${editURL}">Editer</a>
            </div>    
        </div>
    </jsp:body>
</t:genericuserpage>