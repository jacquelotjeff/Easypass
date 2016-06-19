<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Edition d'une catégorie <c:out value="${category.getName()}"/>
    </jsp:attribute>
    <jsp:body>
        <h1>Edition la catégorie <c:out value="${category.getName()}"/></h1>
        <jsp:include page="form.jsp"/>
    </jsp:body>
</t:genericadminpage>