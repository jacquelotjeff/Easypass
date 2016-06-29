<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="fr.easypass.model.Password" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Edition de l'utilisateur <c:out value="${user.getUsername()}"/>
    </jsp:attribute>
    <jsp:body>
        <h1>Editer l'utilisateur <c:out value="${user.getUsername()}"/></h1>
        <jsp:include page="form.jsp"/>
    </jsp:body>
</t:genericadminpage>