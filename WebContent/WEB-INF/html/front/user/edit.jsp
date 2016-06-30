<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="fr.easypass.model.Password" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericuserpage>
    <jsp:attribute name="title">
      Easypass - Edition de mon profil <c:out value="${user.getUsername()}"/>
    </jsp:attribute>
    <jsp:body>
        <h3>Editer mon profil <c:out value="${user.getUsername()}"/></h3>
        <jsp:include page="form.jsp"/>
    </jsp:body>
</t:genericuserpage>