<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Edition du mot de passe <c:out value="${password.getPassword()}"/>
    </jsp:attribute>
    <jsp:body>
        <h1>Editer le mot de passe <c:out value="${password.getPassword()}"/></h1>
        <jsp:include page="form.jsp">
            <jsp:param name="user" value="${password}"/>
        </jsp:include>
    </jsp:body>
</t:genericadminpage>