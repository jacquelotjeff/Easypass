<%@page import="fr.easypass.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %>


<t:genericpage>
    <jsp:attribute name="title">
      Easypass - Liste des utilisateurs
    </jsp:attribute>
    <jsp:body>
        Liste des utilisateurs
        Mon foreach est la
        <c:forEach var="user" items="{'test','encore'}">
            <c:out value="${}"/>
        </c:forEach>
    </jsp:body>
</t:genericpage>