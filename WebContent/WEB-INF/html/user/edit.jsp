<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>
    <jsp:attribute name="title">
      Easypass - Edition de l'utilisateur ${user}
    </jsp:attribute>
    <jsp:body>
        Edition d'un utilisateur
        <jsp:directive.include file="form.jsp">
            <jsp:param name="user" value="${user}"/>
        </jsp:directive.include>
    </jsp:body>
</t:genericpage>