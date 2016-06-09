<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Edition d'un groupe <c:out value="${group.getName()}"/>
    </jsp:attribute>
    <jsp:body>
        <h1>Editer le groupe <c:out value="${group.getName()}"/></h1>
        <jsp:include page="form.jsp"/>
    </jsp:body>
</t:genericadminpage>