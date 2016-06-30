<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Edition du mot de passe <c:out value="${password.getTitle()}"/>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="${pageContext.request.contextPath}/js/password-spoiler.js"></script>
    </jsp:attribute>
    <jsp:body>
        <h1>Editer le mot de passe <c:out value="${password.getTitle()}"/></h1>
        <jsp:include page="form.jsp">
            <jsp:param name="user" value="${password}"/>
        </jsp:include>
    </jsp:body>
</t:genericadminpage>